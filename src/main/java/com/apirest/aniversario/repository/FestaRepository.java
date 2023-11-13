package com.apirest.aniversario.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.apirest.aniversario.entities.Festa;

public interface FestaRepository extends CrudRepository<Festa, Long>{

    Collection<Festa> findAll();
}
