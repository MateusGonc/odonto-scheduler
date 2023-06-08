package com.br.odontoscheduler.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, S extends MongoRepository<T, String>> {

    public abstract S getRepository();

    public T save(T entity) {
        return this.getRepository().save(entity);
    }

    public Page<T> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.getRepository().findAll(pageable);
    }

    public Optional<T> findById(String id) {
        return this.getRepository().findById(id);
    }

    public void delete(String id) {
        this.getRepository().deleteById(id);
    }
}
