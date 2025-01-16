package com.labprog.PortalEgressos.DTO;

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
}
