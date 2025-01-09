package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
