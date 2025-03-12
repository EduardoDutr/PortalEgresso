package com.labprog.PortalEgressos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CursoEgresso {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_curso_egresso", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_egresso", nullable = false)
    @JsonIgnoreProperties("cursos")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnoreProperties("egressos")
    private Curso curso;

    @Column(nullable = false)
    private Integer anoInicio;

    @Column(nullable = false)
    private Integer anoFim;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CursoEgresso that = (CursoEgresso) o;
        return Objects.equals(egresso, that.egresso) && Objects.equals(curso, that.curso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(egresso, curso);
    }
}
