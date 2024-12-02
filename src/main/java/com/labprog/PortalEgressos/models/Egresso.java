package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
@Entity
public class Egresso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_egresso")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    private String descricao;

    private String foto;

    private String linkedin;

    private String instagram;

    private String curriculo;

}
