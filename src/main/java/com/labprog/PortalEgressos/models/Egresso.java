package com.labprog.PortalEgressos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Egresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;

    @OneToMany(mappedBy = "egresso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("egresso")
    private List<Depoimento> depoimentos;

    @OneToMany(mappedBy = "egresso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("egresso")
    private List<Cargo> cargos;

    @OneToMany(mappedBy = "egresso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("egresso")
    private List<CursoEgresso> cursos;
}
