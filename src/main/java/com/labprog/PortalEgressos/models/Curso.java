package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "nivel", nullable = false)
    private String nivel;

}
