package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.CursoDTO;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;

    @GetMapping
    @RequestMapping(value = "/obter/{cursoId}")
    public ResponseEntity obter(@PathVariable Long cursoId){
        try{
            Curso result = cursoService.obter(cursoId);
            CursoDTO resultDTO = new CursoDTO(result);
            return ResponseEntity.ok(resultDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody CursoDTO cursoDTO){
        try{
            Curso curso = Curso.builder()
                    .nome(cursoDTO.getNome())
                    .nivel(cursoDTO.getNivel())
                    .build();

            Curso salvo = cursoService.salvar(curso);
            CursoDTO salvoDTO = new CursoDTO(salvo);

            return ResponseEntity.ok(salvoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    @RequestMapping(value = "/deletar/{cursoId}")
    public ResponseEntity deletar(@PathVariable Long cursoId){
        try {
            cursoService.deletar(cursoId);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping(value = "/associar/{egressoId}/{cursoId}")
    public ResponseEntity associar(@PathVariable Long cursoId, @PathVariable Long egressoId){
        try{
            cursoService.associarEgresso(cursoId, egressoId);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping(value = "/obterPorEgresso/{egressoId}")
    public ResponseEntity obterPorEgresso(@PathVariable Long egressoId){
        try {
            List<Curso> result = cursoService.obterPorEgresso(egressoId);

            List<CursoDTO> resultDTO = result.stream()
                    .map(CursoDTO::new)
                    .toList();

            return ResponseEntity.ok(resultDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
