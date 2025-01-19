package com.labprog.PortalEgressos.DTO;

import com.labprog.PortalEgressos.models.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoDTO {
    private Long id;
    private String descricao;
    private String local;
    private Long anoInicio;
    private Long anoFim;

    private Long egressoId;

    public CargoDTO(Cargo cargo) {
        this.id = cargo.getId();
        this.descricao = cargo.getDescricao();
        this.local = cargo.getLocal();
        this.anoInicio = cargo.getAnoInicio();
        this.anoFim = cargo.getAnoFim();
        this.egressoId = cargo.getEgresso().getId();
    }
}
