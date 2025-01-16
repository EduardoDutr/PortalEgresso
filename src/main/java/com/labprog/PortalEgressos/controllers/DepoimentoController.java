package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.DepoimentoDTO;
import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.service.DepoimentoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepoimentoController {

    @Autowired
    DepoimentoService depoimentoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody DepoimentoDTO depoimentoDTO){
        try {
            Depoimento depoimento = Depoimento.builder()
                    .texto(depoimentoDTO.getTexto())
                    .data(depoimentoDTO.getData())
                    .build();
            depoimentoService.salvar(depoimento, depoimentoDTO.getEgressoId());
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
