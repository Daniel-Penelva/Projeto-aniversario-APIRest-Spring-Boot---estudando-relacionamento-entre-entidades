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
import com.apirest.aniversario.entities.Pessoa;
import com.apirest.aniversario.repository.PessoaRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pessoa")
@AllArgsConstructor
public class PessoaController {

    private PessoaRepository pessoaRepository;

    // http://localhost:8080/api/pessoa/all
    @GetMapping("/all")
    public ResponseEntity<Collection<Pessoa>> findAll() {
        return new ResponseEntity<>(pessoaRepository.findAll(), HttpStatus.OK);
    }

    // http://localhost:8080/api/pessoa/search/{id}
    @GetMapping("/search/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        Pessoa buscarIdPessoa = pessoaRepository.findById(id).orElseThrow();

        if (buscarIdPessoa != null) {
            return new ResponseEntity<>(pessoaRepository.findById(id).orElseThrow(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8080/api/pessoa/create
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Pessoa pessoa) {
        return new ResponseEntity<>(pessoaRepository.save(pessoa), HttpStatus.CREATED);
    }

    // http://localhost:8080/api/pessoa/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        pessoaRepository.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // http://localhost:8080/api/pessoa/search/{id}/festas
    @GetMapping("search/{id}/festas")
    public ResponseEntity<Collection<Festa>> findAllFestaDaPessoa(@PathVariable Long id) {
        Pessoa buscarIdPessoa = pessoaRepository.findById(id).orElseThrow();

        if (buscarIdPessoa != null) {
            return new ResponseEntity<>(buscarIdPessoa.getFestas(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
