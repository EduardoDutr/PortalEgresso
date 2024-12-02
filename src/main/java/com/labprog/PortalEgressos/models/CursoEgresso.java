package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
@Entity
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
    @Column(name = "ano_inicio", nullable = false)
    private Long anoInicio;
    @Column(name = "ano_fim")
    private Long anoFim;
}
