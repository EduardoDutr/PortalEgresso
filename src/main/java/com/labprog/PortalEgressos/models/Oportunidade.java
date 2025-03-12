package com.labprog.PortalEgressos.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Oportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE,
        REJECTED,
        PENDING
    }
}
