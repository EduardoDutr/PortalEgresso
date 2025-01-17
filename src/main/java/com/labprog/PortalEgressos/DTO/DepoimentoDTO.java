package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Depoimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepoimentoDTO {
    private Long id;
    private String texto;
    private Date data;

    private Long egressoId;

    public DepoimentoDTO(Depoimento depoimento){
        this.id = depoimento.getId();
        this.texto = depoimento.getTexto();
        this.data = depoimento.getData();

        this.egressoId = depoimento.getEgresso().getId();
    }
}
