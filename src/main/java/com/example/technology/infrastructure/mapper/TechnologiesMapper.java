package com.example.technology.infrastructure.mapper;

import com.example.technology.domain.model.*;
import com.example.technology.infrastructure.repository.documents.TechnologyEntity;

public class TechnologiesMapper {
  public static Technology toDomain(TechnologyEntity item){
    return new Technology(
            item.getId(), item.getName(),
            item.getDescription()
    );
  }
  public static TechnologyEntity toEntity(Technology domain){
    var entity = new TechnologyEntity();
    entity.setId(domain.id()); entity.setName(domain.name());
    entity.setDescription(domain.description());
    return entity;
  }
}

