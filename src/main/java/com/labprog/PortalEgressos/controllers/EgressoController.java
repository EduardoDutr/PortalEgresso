package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.EgressoDTO;
import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.Egresso;
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
    public ResponseEntity obterEgressoPorId(@PathVariable Integer egressoId){
        try{
            Egresso egresso = egressoService.obterPorId(egressoId.longValue());

            EgressoDTO egressoDTO = new EgressoDTO(egresso);

            return ResponseEntity.ok(egressoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterTodos")
    public ResponseEntity obterTodosEgressos(){
        try {
            List<Egresso> egressos = egressoService.obterTodos();

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return ResponseEntity.ok(egressosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorAno/{ano}")
    public ResponseEntity obterPorAno(@PathVariable Integer ano){
        try {
            Set<Egresso> egressos = egressoService.obterPorAno(ano.longValue());

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return ResponseEntity.ok(egressosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorCargo")
    public ResponseEntity obterPorCargo(@RequestBody Long cargoId){
        try {
            Egresso egresso = egressoService.obterPorCargo(cargoId);
            EgressoDTO egressoDTO = new EgressoDTO(egresso);
            return new ResponseEntity(egressoDTO, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorCurso")
    public ResponseEntity obterPorCurso(@RequestBody Long cursoId){
        try {
            Set<Egresso> egressos = egressoService.obterPorCurso(cursoId);

            List<EgressoDTO> egressosDTO = egressos.stream()
                    .map(EgressoDTO::new)
                    .toList();

            return new ResponseEntity(egressosDTO, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody Egresso egresso){
        try {
            egressoService.salvar(egresso);
            return ResponseEntity.ok(HttpStatus.CREATED);
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

}
