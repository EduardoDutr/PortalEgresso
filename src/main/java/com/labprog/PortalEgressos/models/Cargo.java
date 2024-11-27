package com.labprog.PortalEgressos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Cargo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long id;
    @Column(name = "descrição")
    private String descricao;
    private String local;
    @Column(name = "ano_inicio")
    private Long anoInicio;
    @Column(name = "ano_fim")
    private Long anoFim;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    private Egresso egresso;
}
