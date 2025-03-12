package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.DepoimentoDTO;
import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.service.DepoimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/depoimento")
public class DepoimentoController {

    @Autowired
    DepoimentoService depoimentoService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<DepoimentoDTO> salvar(@RequestBody DepoimentoDTO depoimentoDTO){
        Depoimento depoimento = Depoimento.builder()
                .texto(depoimentoDTO.getTexto())
                .data(depoimentoDTO.getData())
                .build();
        Depoimento salvo = depoimentoService.salvar(depoimento, depoimentoDTO.getEgressoId());
        DepoimentoDTO salvoDTO = new DepoimentoDTO(salvo);
        return ResponseEntity.ok(salvoDTO);
    }

    @DeleteMapping(value = "/deletar/{depoimentoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long depoimentoId){
        depoimentoService.delete(depoimentoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/obterPorEgresso/{egressoId}")
    public ResponseEntity<List<DepoimentoDTO>> obterPorEgresso(@PathVariable Long egressoId){
        List<Depoimento> depoimentos = depoimentoService.obterPorEgresso(egressoId);
        List<DepoimentoDTO> depoimentosDTO = depoimentos.stream().map(DepoimentoDTO::new).toList();
        return ResponseEntity.ok(depoimentosDTO);
    }
}
