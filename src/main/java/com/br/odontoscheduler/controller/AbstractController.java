package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.model.base.BaseEntity;
import com.br.odontoscheduler.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController<S extends BaseEntity, T extends AbstractService> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract T getService();

    @GetMapping
    public ResponseEntity<List<S>> getAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Page<S> objectPage = this.getService().findAll(page, size);

        List<S> objectList = objectPage.getContent();

        return new ResponseEntity<List<S>>(objectList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<S> getById(@PathVariable String id) {
        logger.info("getting " + id);
        Optional<S> optionalObj = this.getService().findById(id);

        if (optionalObj.isPresent()) {
            return new ResponseEntity<S>(optionalObj.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<S> save(@RequestBody S object) {
        logger.info("saving " + object.toString());
        object.setCreatedAt(LocalDateTime.now());

        return new ResponseEntity<S>((S) this.getService().save(object), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        logger.info("deleting " + id);
        this.getService().delete(id);

        return ResponseEntity.ok().build();
    }
}
