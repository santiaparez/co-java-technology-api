package com.example.technology.domain.usecase;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.repository.SpringDataTechnologyRepository;
import reactor.core.publisher.Flux;

public class GetAllTechnologiesUseCase {
    private final SpringDataTechnologyRepository repo;

    public GetAllTechnologiesUseCase(SpringDataTechnologyRepository repo) {
        this.repo = repo;
    }

    public Flux<Technology> execute() {
        return repo.findAll();
    }
}

