package com.example.technology.domain.usecase;

import com.example.technology.domain.error.DomainException;
import com.example.technology.domain.error.ErrorCodes;
import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

public class CreateTechnologyUseCase {
  private final SpringDataTechnologyRepository repo;
  public CreateTechnologyUseCase(SpringDataTechnologyRepository repo) { this.repo = repo; }
  public Mono<Technology> execute(String name, String description) {
    return repo.findByName(name)
        .flatMap(existing -> Mono.<Technology>error(
            new DomainException(ErrorCodes.CONFLICT, "technology.name.already.exists")
        ))
        .switchIfEmpty(Mono.defer(() ->
            repo.save(new Technology(UUID.randomUUID().toString(), name, description))
        ));
  }
}
