package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CursoDTO {
    private Long id;
    private String nome;
    private String nivel;

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.nivel = curso.getNivel();
    }
}
