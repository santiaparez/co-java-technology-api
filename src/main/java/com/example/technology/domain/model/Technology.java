package com.example.technology.domain.model;


public record Technology(String id, String name, String description) {
  public Technology {
    if (name == null || name.isBlank() && name.length() > 50) throw new IllegalArgumentException("invalid.tech.name");
    if (description == null || description.isBlank() && description.length() > 90) throw new IllegalArgumentException("invalid.tech.description");
  }
}
