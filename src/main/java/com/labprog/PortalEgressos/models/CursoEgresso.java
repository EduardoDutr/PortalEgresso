package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class CursoEgresso {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_curso_egresso")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    @Column(name = "ano_inicio")
    private Long anoInicio;
    @Column(name = "ano_fim")
    private Long anoFim;
}
