package com.example.technology.infrastructure.repository;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.mapper.TechnologiesMapper;
import com.example.technology.infrastructure.repository.documents.TechnologyEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SpringDataTechnologyRepository {

  private final R2dbcEntityTemplate template;

  public SpringDataTechnologyRepository(R2dbcEntityTemplate template) {
    this.template = template;
  }

  public Mono<Technology> findById(String id){
    return template.selectOne(Query.query(Criteria.where("id").is(id)), TechnologyEntity.class)
            .map(TechnologiesMapper::toDomain);
  }

  public Mono<Technology> save(Technology franchise){
    var entity = TechnologiesMapper.toEntity(franchise);
    return template.insert(TechnologyEntity.class)
            .using(entity)
            .map(saved -> franchise);
  }

  public Flux<Technology> findAll(){
    return template.select(Query.empty(), TechnologyEntity.class)
            .map(TechnologiesMapper::toDomain);
  }
}

