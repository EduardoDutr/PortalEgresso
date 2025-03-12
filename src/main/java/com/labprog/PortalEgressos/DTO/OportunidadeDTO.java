package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.models.Oportunidade.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OportunidadeDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private String status;

    public OportunidadeDTO(Oportunidade oportunidade) {
        this(
                oportunidade.getId(),
                oportunidade.getTitulo(),
                oportunidade.getDescricao(),
                oportunidade.getUrl(),
                Optional.ofNullable(oportunidade.getStatus()).map(Status::name).orElse(null)
        );
    }

    public Oportunidade toOportunidade() {
        return new Oportunidade(null, titulo, descricao, url, null);
    }
}
