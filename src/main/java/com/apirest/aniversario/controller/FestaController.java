package com.apirest.aniversario.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.aniversario.entities.Festa;
import com.apirest.aniversario.repository.FestaRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/festa")
@AllArgsConstructor
public class FestaController {

    public FestaRepository festaRepository;

    // http://localhost:8080/api/festa/all
    @GetMapping("/all")
    public ResponseEntity<Collection<Festa>> findAll() {
        return new ResponseEntity<>(festaRepository.findAll(), HttpStatus.OK);
    }

    // http://localhost:8080/api/festa/search/{id}
    @GetMapping("/search/{id}")
    public ResponseEntity<Festa> findById(@PathVariable Long id) {
        Festa buscarIdFesta = festaRepository.findById(id).orElseThrow();

        if (buscarIdFesta != null) {
            return new ResponseEntity<>(festaRepository.findById(id).orElseThrow(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8080/api/festa/create
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Festa festa) {
        return new ResponseEntity<>(festaRepository.save(festa), HttpStatus.CREATED);
    }

    // http://localhost:8080/api/festa/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        festaRepository.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
