package com.labprog.PortalEgressos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class Cargo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long id;
    @Column(name = "descrição", nullable = false)
    private String descricao;
    @Column(name = "local", nullable = false)
    private String local;
    @Column(name = "ano_inicio", nullable = false)
    private Long anoInicio;
    @Column(name = "ano_fim")
    private Long anoFim;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    private Egresso egresso;
}
