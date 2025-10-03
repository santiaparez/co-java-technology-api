package com.example.technology.domain.usecase;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class GetAllTechnologyUseCaseTest {

  private final SpringDataTechnologyRepository repo = Mockito.mock(SpringDataTechnologyRepository.class);
  private final GetAllTechnologiesUseCase useCase = new GetAllTechnologiesUseCase(repo);

  @Test
  void delegatesToRepository() {
    Technology technology = new Technology("f-1", "Acme", "des");
    Mockito.when(repo.findAll()).thenReturn(Flux.just(technology));

    StepVerifier.create(useCase.execute())
        .expectNext(technology)
        .verifyComplete();

    Mockito.verify(repo).findAll();
  }
}

