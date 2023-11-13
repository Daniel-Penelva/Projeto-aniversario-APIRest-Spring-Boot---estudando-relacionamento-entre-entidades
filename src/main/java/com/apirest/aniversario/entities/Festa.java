package com.apirest.aniversario.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "festas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Festa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFesta")
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date data;

    private String localizacao;

    //@ManyToMany(cascade = CascadeType.ALL) - retirei porque não se pode eliminar a pessoa, somente a festa
    @ManyToMany
    @JoinTable(name = "pessoas_festas", 
        joinColumns = @JoinColumn(name = "festa_id", referencedColumnName = "idFesta"), 
        inverseJoinColumns = @JoinColumn(name = "pessoa_id", referencedColumnName = "idPessoa"))
    private Set<Pessoa> pessoas = new HashSet<>();

}
