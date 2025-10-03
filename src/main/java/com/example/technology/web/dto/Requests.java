package com.example.technology.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Requests {
  public record CreateTechnologyRequest(@NotBlank String name, String description) {}
}
