package com.example.technology.infrastructure.mapper;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.repository.documents.TechnologyItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyMapperTest {

  @Test
  void mapsItemToDomain() {


    TechnologyItem item = new TechnologyItem();
    item.setId("f-1");
    item.setName("Acme");
    item.setDescription("des");

    Technology domain = TechnologiesMapper.toDomain(item);

    assertEquals("f-1", domain.id());
    assertEquals("Acme", domain.name());
    assertEquals("des", domain.description());
  }

  @Test
  void mapsDomainToItem() {
    Technology domain = new Technology("f-1", "Acme", "des");

    TechnologyItem item = TechnologiesMapper.toItem(domain);

    assertEquals("f-1", item.getId());
    assertEquals("Acme", item.getName());
    assertEquals("des", item.getDescription());
  }

}

