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
    EgressoService egressoService;

    @GetMapping(value = "/obter/{egressoId}")
    public ResponseEntity obterEgressoPorId(@PathVariable Long egressoId){
        try{
            Egresso egresso = egressoService.obterPorId(egressoId);

            EgressoDTO egressoDTO = new EgressoDTO(egresso);

            return ResponseEntity.ok(egressoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterTodos")
    public ResponseEntity obterTodosEgressos(){
        try {
            List<Egresso> egressos = egressoService.ativos();

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return ResponseEntity.ok(egressosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterPorAno/{ano}")
    public ResponseEntity obterPorAno(@PathVariable Long ano){
        try {
            Set<Egresso> egressos = egressoService.obterPorAno(ano);

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return ResponseEntity.ok(egressosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterPorCargoId/{cargoId}")
    public ResponseEntity obterPorCargoId(@PathVariable Long cargoId){
        try {
            Egresso egresso = egressoService.obterPorCargo(cargoId);
            EgressoDTO egressoDTO = new EgressoDTO(egresso);
            return ResponseEntity.ok(egressoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterPorCursoId/{cursoId}")
    public ResponseEntity obterPorCursoId(@PathVariable Long cursoId){
        try {
            Set<Egresso> egressos = egressoService.obterPorCurso(cursoId);

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return ResponseEntity.ok(egressosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody Egresso egresso){
        try {
            Egresso salvo = egressoService.salvar(egresso);
            EgressoDTO egressoDTO = new EgressoDTO(salvo);
            return ResponseEntity.ok(egressoDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar/{egressoId}")
    public ResponseEntity deletar(@PathVariable Long egressoId){
        try {
            egressoService.deletar(egressoId);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<EgressoDTO>> obterPendentes() {
        List<EgressoDTO> egressos = egressoService.obterPendentes().stream().map(EgressoDTO::new).toList();
        return ResponseEntity.ok().body(egressos);
    }

    @PutMapping(value = "/{egressoId}/{status}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long egressoId, @PathVariable Status status) {
        switch (status) {
            case ACTIVE -> egressoService.ativar(egressoId);
            case REJECTED -> egressoService.deletar(egressoId);
        }
        return ResponseEntity.noContent().build();
    }
}
