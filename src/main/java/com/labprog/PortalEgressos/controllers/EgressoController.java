package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.EgressoDTO;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.models.Egresso.Status;
import com.labprog.PortalEgressos.service.EgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/egresso")
public class EgressoController {

    @Autowired
    private EgressoService service;

    @GetMapping(value = "/obter/{egressoId}")
    public ResponseEntity<?> obterEgressoPorId(@PathVariable Long egressoId){
        Egresso egresso = service.obterPorId(egressoId);
        EgressoDTO egressoDTO = new EgressoDTO(egresso);
        return ResponseEntity.ok(egressoDTO);
    }

    @GetMapping(value = "/obterTodos")
    public ResponseEntity<?> obterTodosEgressos(){
        List<Egresso> egressos = service.ativos();
        List<EgressoDTO> egressosDTO = egressos.stream().map(EgressoDTO::new).toList();
        return ResponseEntity.ok(egressosDTO);
    }

    @GetMapping(value = "/obterPorAno/{ano}")
    public ResponseEntity<?> obterPorAno(@PathVariable Long ano){
        Set<Egresso> egressos = service.obterPorAno(ano);
        List<EgressoDTO> egressosDTO = egressos.stream().map(EgressoDTO::new).toList();
        return ResponseEntity.ok(egressosDTO);
    }

    @GetMapping(value = "/obterPorCursoId/{cursoId}")
    public ResponseEntity<?> obterPorCursoId(@PathVariable Long cursoId){
        Set<Egresso> egressos = service.obterPorCurso(cursoId);
        List<EgressoDTO> egressosDTO = egressos.stream().map(EgressoDTO::new).toList();
        return ResponseEntity.ok(egressosDTO);
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<?> salvar(@RequestBody Egresso egresso){
        Egresso salvo = service.salvar(egresso);
        EgressoDTO egressoDTO = new EgressoDTO(salvo);
        return ResponseEntity.ok(egressoDTO);
    }

    @DeleteMapping(value = "/deletar/{egressoId}")
    public ResponseEntity<?> deletar(@PathVariable Long egressoId){
        service.deletar(egressoId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<EgressoDTO>> obterPendentes() {
        List<EgressoDTO> egressos = service.obterPendentes().stream().map(EgressoDTO::new).toList();
        return ResponseEntity.ok().body(egressos);
    }

    @PutMapping(value = "/{egressoId}/{status}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long egressoId, @PathVariable Status status) {
        switch (status) {
            case ACTIVE -> service.ativar(egressoId);
            case REJECTED -> service.deletar(egressoId);
        }
        return ResponseEntity.noContent().build();
    }
}
