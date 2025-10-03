package com.example.technology.infrastructure.repository;

import com.example.technology.infrastructure.repository.documents.TechnologyItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpringDataTechnologyRepositoryTest {

  private final DynamoDbAsyncTable<TechnologyItem> table = Mockito.mock(DynamoDbAsyncTable.class);
  private final SpringDataTechnologyRepository repository = new SpringDataTechnologyRepository(table);

  private static TechnologyItem sampleItem() {

    TechnologyItem item = new TechnologyItem();
    item.setId("f-1");
    item.setName("Acme");
    item.setDescription("des");
    return item;
  }

  @Test
  void findAllStreamsFromScan() {
    TechnologyItem item = sampleItem();
    PagePublisher<TechnologyItem> publisher = Mockito.mock(PagePublisher.class);
    Mockito.when(publisher.items()).thenReturn(SdkPublisher.adapt(reactor.core.publisher.Flux.just(item)));
    Mockito.when(table.scan()).thenReturn(publisher);

    StepVerifier.create(repository.findAll())
        .assertNext(franchise -> assertEquals("Acme", franchise.name()))
        .verifyComplete();

    Mockito.verify(table).scan();
  }
}

