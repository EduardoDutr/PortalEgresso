package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.OportunidadeDTO;
import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.models.Oportunidade.Status;
import com.labprog.PortalEgressos.service.OportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/oportunidade")
public class OportunidadeController {

    @Autowired
    private OportunidadeService service;

    @GetMapping("/pendentes")
    public ResponseEntity<List<OportunidadeDTO>> obterPendentes() {
        List<OportunidadeDTO> egressos = service.obterPendentes().stream().map(OportunidadeDTO::new).toList();
        return ResponseEntity.ok().body(egressos);
    }

    @GetMapping
    public ResponseEntity<?> obterAtivos(){
        List<Oportunidade> egressos = service.obterAtivos();
        List<OportunidadeDTO> egressosDTO = egressos.stream().map(OportunidadeDTO::new).toList();
        return ResponseEntity.ok(egressosDTO);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody OportunidadeDTO oportunidadeDTO){
        Oportunidade salvo = service.salvar(oportunidadeDTO.toOportunidade());
        OportunidadeDTO egressoDTO = new OportunidadeDTO(salvo);
        return ResponseEntity.ok(egressoDTO);
    }

    @DeleteMapping(value = "/{oportunidadeId}")
    public ResponseEntity<?> deletar(@PathVariable Long oportunidadeId){
        service.deletar(oportunidadeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{oportunidadeId}/{status}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long oportunidadeId, @PathVariable Status status) {
        switch (status) {
            case ACTIVE -> service.ativar(oportunidadeId);
            case REJECTED -> service.deletar(oportunidadeId);
        }
        return ResponseEntity.noContent().build();
    }
}
