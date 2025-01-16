package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Coordenador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadorDTO {
    private Long id;
    private String login;
    private String senha;

    public CoordenadorDTO(Coordenador coordenador){
        this.id = coordenador.getId();
        this.login = coordenador.getLogin();
        this.senha = coordenador.getSenha();
    }
}
