package com.example.technology.application.config;

import com.example.technology.web.handler.TechnologyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
  @Bean
  public RouterFunction<ServerResponse> routes(TechnologyHandler h) {
    return RouterFunctions.nest(RequestPredicates.path("/api/v1"),
      RouterFunctions.route()
        .POST("/technology", h::createTechnology)
        .GET("/technology", h::getAllTechnology)
        .build()
    );
  }
}
