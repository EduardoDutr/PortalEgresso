package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoEgresso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_curso_egresso", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @Column(nullable = false)
    private Long anoInicio;

    private Long anoFim;
}
