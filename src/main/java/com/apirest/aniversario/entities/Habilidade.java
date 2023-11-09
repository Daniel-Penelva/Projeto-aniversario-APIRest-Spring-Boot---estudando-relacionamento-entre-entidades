package com.apirest.aniversario.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habilidades")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHabilidade")
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    // Muitas habilidades para uma pessoa
    @ManyToOne
    @JoinColumn(name = "pessoaId")
    @JsonBackReference
    private Pessoa pessoa;
    
}
