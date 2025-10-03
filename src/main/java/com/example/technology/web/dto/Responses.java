package com.example.technology.web.dto;


public class Responses {
  public record IdResponse(String id) {}
  public record FranchiseResponse(String id, String name, String description) {}
}
