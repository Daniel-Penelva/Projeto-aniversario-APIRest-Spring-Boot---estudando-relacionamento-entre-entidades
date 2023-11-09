package com.apirest.aniversario.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPessoa")
    private Long id;

    private String nome;
    private int idade;

    // Uma pessoa pode ter muitas habilidades
    @OneToMany(mappedBy = "pessoa")
    private Set<Habilidade> habilidades = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"), 
        inverseJoinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"))
    private Set<Festa> festas = new HashSet<>();
}
