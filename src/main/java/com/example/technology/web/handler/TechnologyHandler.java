package com.example.technology.web.handler;

import com.example.technology.domain.error.DomainException;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.usecase.*;
import com.example.technology.web.dto.Requests.*;
import com.example.technology.web.dto.Responses.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class TechnologyHandler {
  private final Validator validator;
  private final CreateTechnologyUseCase createFranchise;
  private final GetAllTechnologiesUseCase getAllTechnologies;

  public TechnologyHandler(Validator validator, CreateTechnologyUseCase createFranchise, GetAllTechnologiesUseCase getAllTechnologies) {
    this.validator = validator; this.createFranchise = createFranchise; this.getAllTechnologies = getAllTechnologies;
  }

  public Mono<ServerResponse> createTechnology(ServerRequest req){
    return validatedBody(req, CreateTechnologyRequest.class, body ->
      createFranchise.execute(body.name(), body.description()).flatMap(f -> okJson(new IdResponse(f.id())))
    );
  }

  public Mono<ServerResponse> getAllTechnology(ServerRequest req) {
    return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(getAllTechnologies.execute().map(Mapper::technology), FranchiseResponse.class);
  }


  // helpers
  private static class Mapper {
    static FranchiseResponse technology(Technology f){
      return new FranchiseResponse(
        f.id(), f.name(), f.description()
      );
    }
  }

  private <T> Mono<ServerResponse> validatedBody(ServerRequest req, Class<T> clazz, java.util.function.Function<T, Mono<ServerResponse>> fn){
    return req.bodyToMono(clazz).flatMap(body -> {
      var errors = new BeanPropertyBindingResult(body, clazz.getSimpleName());
      validator.validate(body, errors);
      if (errors.hasErrors()) {
        String msg = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()==null?"validation.error":e.getDefaultMessage()).reduce((a,b) -> a+","+b).orElse("validation.error");
        return problem(400, msg);
      }
      return fn.apply(body);
    }).onErrorResume(DomainException.class, ex -> problem(mapHttp(ex.getCode()), ex.getMessage()));
  }

  private Mono<ServerResponse> okJson(Object any){
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(any));
  }
  private Mono<ServerResponse> problem(int status, String message){
    return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON).body(fromValue(java.util.Map.of("message", message)));
  }
  private int mapHttp(com.example.technology.domain.error.ErrorCodes code){
    return switch (code){
      case TECHNOLOGY_NOT_FOUND, BRANCH_NOT_FOUND, PRODUCT_NOT_FOUND -> 404;
      case VALIDATION_ERROR -> 400;
      case CONFLICT -> 409;
      default -> 500;
    };
  }
}
