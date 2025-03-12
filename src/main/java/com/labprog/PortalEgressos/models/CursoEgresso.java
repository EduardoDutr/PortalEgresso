package com.labprog.PortalEgressos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CursoEgresso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_curso_egresso", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    @JsonIgnoreProperties("cursos")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnoreProperties("egressos")
    private Curso curso;

    @Column(nullable = false)
    private Integer anoInicio;

    @Column(nullable = false)
    private Integer anoFim;
}
