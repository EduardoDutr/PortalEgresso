package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Egresso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EgressoDTO {
    private Long id;
    private String nome;
    private String email;
    private String descricao;
    private String foto;
    private String linkedin;
    private String instagram;
    private String curriculo;

    public EgressoDTO(Egresso egresso) {
        this.id = egresso.getId();
        this.nome = egresso.getNome();
        this.email = egresso.getEmail();
        this.descricao = egresso.getDescricao();
        this.foto = egresso.getFoto();
        this.linkedin = egresso.getLinkedin();
        this.instagram = egresso.getInstagram();
        this.curriculo = egresso.getCurriculo();
    }
}
