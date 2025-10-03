package com.example.technology.infrastructure.repository;

import com.example.technology.infrastructure.repository.documents.TechnologyEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveInsertOperation;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class SpringDataTechnologyRepositoryTest {

  private final R2dbcEntityTemplate template = Mockito.mock(R2dbcEntityTemplate.class);
  private final SpringDataTechnologyRepository repository = new SpringDataTechnologyRepository(template);

  private static TechnologyEntity sampleEntity() {
    TechnologyEntity entity = new TechnologyEntity();
    entity.setId("f-1");
    entity.setName("Acme");
    entity.setDescription("des");
    return entity;
  }

  @Test
  void findAllStreamsFromTemplate() {
    TechnologyEntity entity = sampleEntity();
    Mockito.when(template.select(Mockito.any(Query.class), Mockito.eq(TechnologyEntity.class)))
        .thenReturn(Flux.just(entity));

    StepVerifier.create(repository.findAll())
        .assertNext(franchise -> assertEquals("Acme", franchise.name()))
        .verifyComplete();

    Mockito.verify(template).select(Mockito.any(Query.class), Mockito.eq(TechnologyEntity.class));
  }

  @Test
  void saveDelegatesToTemplateInsert() {
    TechnologyEntity entity = sampleEntity();
    @SuppressWarnings("unchecked")
    ReactiveInsertOperation.InsertWithTable<TechnologyEntity> insert = Mockito.mock(ReactiveInsertOperation.InsertWithTable.class);
    Mockito.when(template.insert(TechnologyEntity.class)).thenReturn(insert);
    Mockito.when(insert.using(Mockito.any(TechnologyEntity.class))).thenReturn(Mono.just(entity));

    StepVerifier.create(repository.save(new com.example.technology.domain.model.Technology("f-1", "Acme", "des")))
        .assertNext(franchise -> assertEquals("Acme", franchise.name()))
        .verifyComplete();

    Mockito.verify(insert).using(Mockito.any(TechnologyEntity.class));
  }

  @Test
  void findByIdUsesTemplate() {
    TechnologyEntity entity = sampleEntity();
    Mockito.when(template.selectOne(Mockito.any(Query.class), Mockito.eq(TechnologyEntity.class)))
        .thenReturn(Mono.just(entity));

    StepVerifier.create(repository.findById("f-1"))
        .assertNext(franchise -> assertEquals("Acme", franchise.name()))
        .verifyComplete();

    Mockito.verify(template).selectOne(Mockito.any(Query.class), Mockito.eq(TechnologyEntity.class));
  }
}

