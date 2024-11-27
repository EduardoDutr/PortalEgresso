package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Egresso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_egresso")
    private Long id;
    private String nome;
    private String email;
    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;

}
