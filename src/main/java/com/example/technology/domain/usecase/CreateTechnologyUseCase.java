package com.example.technology.domain.usecase;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

public class CreateTechnologyUseCase {
  private final SpringDataTechnologyRepository repo;
  public CreateTechnologyUseCase(SpringDataTechnologyRepository repo) { this.repo = repo; }
  public Mono<Technology> execute(String name, String description) {
    return repo.save(new Technology(UUID.randomUUID().toString(), name, description));
  }
}
