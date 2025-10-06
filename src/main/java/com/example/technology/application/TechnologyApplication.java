package com.example.technology.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.technology")
public class TechnologyApplication {
  public static void main(String[] args) { SpringApplication.run(TechnologyApplication.class, args); }
}
