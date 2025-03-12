package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Oportunidade;

public record OportunidadeDTO (
        Long id,
        String titulo,
        String descricao,
        String url,
        String status
) {
    public OportunidadeDTO(Oportunidade oportunidade) {
        this(
                oportunidade.getId(),
                oportunidade.getTitulo(),
                oportunidade.getDescricao(),
                oportunidade.getUrl(),
                oportunidade.getStatus().name()
        );
    }

    public Oportunidade toOportunidade() {
        return new Oportunidade(null, titulo, descricao, url, null);
    }
}
