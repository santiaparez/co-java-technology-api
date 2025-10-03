package com.example.technology.application.config;

import com.example.technology.domain.usecase.*;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public CreateTechnologyUseCase createTechnologyUseCase(SpringDataTechnologyRepository repo) {
        return new CreateTechnologyUseCase(repo);
    }

    @Bean
    public GetAllTechnologiesUseCase getAllTechnologyUseCase(SpringDataTechnologyRepository repo) {
        return new GetAllTechnologiesUseCase(repo);
    }


}

