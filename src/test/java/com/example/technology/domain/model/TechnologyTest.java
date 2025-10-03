package com.example.technology.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyTest {

  @Test
  void createFranchise_ok() {
    Technology technology = new Technology("f-1", "Acme", "des");

    assertEquals("f-1", technology.id());
    assertEquals("Acme", technology.name());
    assertEquals("des", technology.description());
  }


}

