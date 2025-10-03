package com.example.technology.infrastructure.mapper;

import com.example.technology.domain.model.*;
import com.example.technology.infrastructure.repository.documents.TechnologyItem;

public class TechnologiesMapper {
  public static Technology toDomain(TechnologyItem item){
    return new Technology(
            item.getId(), item.getName(),
            item.getDescription()
    );
  }
  public static TechnologyItem toItem(Technology domain){
    var item = new TechnologyItem();
    item.setId(domain.id()); item.setName(domain.name());
    item.setDescription(domain.description());
    return item;
  }
}

