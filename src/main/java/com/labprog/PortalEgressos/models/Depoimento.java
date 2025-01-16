package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Depoimento {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;
}
