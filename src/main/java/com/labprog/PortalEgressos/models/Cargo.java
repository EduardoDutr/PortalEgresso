package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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
