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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/depoimento")
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
            Depoimento salvo = depoimentoService.salvar(depoimento, depoimentoDTO.getEgressoId());
            DepoimentoDTO salvoDTO = new DepoimentoDTO(salvo);
            return ResponseEntity.ok(salvoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar/{depoimentoId}")
    public ResponseEntity deletar(@PathVariable Long depoimentoId){
        try{
            depoimentoService.delete(depoimentoId);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterPorEgresso/{egressoId}")
    public ResponseEntity obterPorEgresso(@PathVariable Long egressoId){
        try{
            List<Depoimento> depoimentos = depoimentoService.obterPorEgresso(egressoId);
            List<DepoimentoDTO> depoimentosDTO = depoimentos.stream()
                    .map(DepoimentoDTO::new)
                    .toList();
            return ResponseEntity.ok(depoimentosDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
