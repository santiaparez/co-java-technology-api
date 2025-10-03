package com.example.technology.infrastructure.repository;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.mapper.TechnologiesMapper;
import com.example.technology.infrastructure.repository.documents.TechnologyItem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;

@Repository
public class SpringDataTechnologyRepository {

  private final DynamoDbAsyncTable<TechnologyItem> table;

  public SpringDataTechnologyRepository(DynamoDbAsyncTable<TechnologyItem> table) {
    this.table = table;
  }

  public Mono<Technology> findById(String id){
    return Mono.fromCompletionStage(table.getItem(r -> r.key(k -> k.partitionValue(id))))
            .map(TechnologiesMapper::toDomain);
  }

  public Mono<Technology> save(Technology franchise){
    var item = TechnologiesMapper.toItem(franchise);
    return Mono.fromCompletionStage(table.putItem(item)).thenReturn(franchise);
  }

  public Flux<Technology> findAll(){
    return Flux.from(table.scan().items()).map(TechnologiesMapper::toDomain);
  }
}

