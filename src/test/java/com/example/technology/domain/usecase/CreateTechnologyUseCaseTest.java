package com.example.technology.domain.usecase;

import com.example.technology.domain.error.DomainException;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.usecase.CreateTechnologyUseCase;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class CreateTechnologyUseCaseTest {
  @Test
  void create_ok(){
    SpringDataTechnologyRepository repo = Mockito.mock(SpringDataTechnologyRepository.class);
    Mockito.when(repo.findByName("My Franchise")).thenReturn(Mono.empty());
    Mockito.when(repo.save(Mockito.any(Technology.class))).thenAnswer(i -> Mono.just((Technology) i.getArguments()[0]));
    var uc = new CreateTechnologyUseCase(repo);
    StepVerifier.create(uc.execute("My Franchise", "des"))
        .assertNext(franchise -> {
          assertNotNull(franchise.id());
          assertEquals("My Franchise", franchise.name());
        })
        .verifyComplete();
  }

  @Test
  void create_conflict_when_name_exists(){
    SpringDataTechnologyRepository repo = Mockito.mock(SpringDataTechnologyRepository.class);
    Mockito.when(repo.findByName("My Franchise")).thenReturn(Mono.just(new Technology("id", "My Franchise", "des")));
    var uc = new CreateTechnologyUseCase(repo);

    StepVerifier.create(uc.execute("My Franchise", "des"))
        .expectErrorSatisfies(error -> {
          assertInstanceOf(DomainException.class, error);
          assertEquals("technology.name.already.exists", error.getMessage());
        })
        .verify();
  }
}
