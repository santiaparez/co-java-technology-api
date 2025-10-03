package com.example.technology.web;

import com.example.technology.application.config.RouterConfig;

import com.example.technology.domain.model.Technology;
import com.example.technology.domain.usecase.*;
import com.example.technology.web.dto.Requests.*;
import com.example.technology.web.handler.TechnologyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import reactor.core.publisher.Mono;

class FranchiseHandlerTest {

  private final CreateTechnologyUseCase create = Mockito.mock(CreateTechnologyUseCase.class);
  private final GetAllTechnologiesUseCase getAll = Mockito.mock(GetAllTechnologiesUseCase.class);

  private WebTestClient client;

  @BeforeEach
  void setUp() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();
    TechnologyHandler handler = new TechnologyHandler(
        validator,
        create,
        getAll
    );
    client = WebTestClient.bindToRouterFunction(new RouterConfig().routes(handler))
        .handlerStrategies(HandlerStrategies.withDefaults())
        .configureClient()
        .baseUrl("/api/v1")
        .build();
  }

  @Test
  void createTech_success() {
    Mockito.when(create.execute("Acme","des")).thenReturn(Mono.just(new Technology("id-1", "Acme", "des")));

    client.post().uri("/technology")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new CreateTechnologyRequest("Acme","des"))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo("id-1");
  }

  @Test
  void createTech_validationError() {
    client.post().uri("/technology")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new CreateTechnologyRequest("",""))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("$.message").isEqualTo("no debe estar vacío");
  }

}

