package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
@Entity
public class Depoimento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    private Egresso egresso;
}
