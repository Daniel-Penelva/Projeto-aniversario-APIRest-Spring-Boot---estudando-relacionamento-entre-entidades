package com.apirest.aniversario.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apirest.aniversario.entities.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    Collection<Pessoa> findAll();
}
