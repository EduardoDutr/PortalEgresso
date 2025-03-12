package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.CursoDTO;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;

    @GetMapping("/obterTodos")
    public ResponseEntity<?> obterTodos(){
        try{
            List<Curso> cursos = cursoService.obterTodos();
            List<CursoDTO> cursosDTO = cursos.stream()
                    .map(CursoDTO::new)
                    .toList();
            return ResponseEntity.ok(cursosDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/obter/{cursoId}")
    public ResponseEntity<?> obter(@PathVariable Long cursoId){
        try{
            Curso result = cursoService.obter(cursoId);
            CursoDTO resultDTO = new CursoDTO(result);
            return ResponseEntity.ok(resultDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody CursoDTO cursoDTO){
        Curso curso = Curso.builder()
                .nome(cursoDTO.getNome())
                .nivel(cursoDTO.getNivel())
                .build();
        Curso salvo = cursoService.salvar(curso);
        CursoDTO salvoDTO = new CursoDTO(salvo);
        return ResponseEntity.ok(salvoDTO);
    }

    @DeleteMapping("/deletar/{cursoId}")
    public ResponseEntity deletar(@PathVariable Long cursoId){
        cursoService.deletar(cursoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/associar")
    public ResponseEntity<?> associar(@RequestBody AssociarEgressoInput input){
        cursoService.associarEgresso(input.egressoId, input.cursoId, input.anoInicio, input.anoFim);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/obterPorEgresso/{egressoId}")
    public ResponseEntity<?> obterPorEgresso(@PathVariable Long egressoId){
        List<Curso> result = cursoService.obterPorEgresso(egressoId);
        List<CursoDTO> resultDTO = result.stream().map(CursoDTO::new).toList();
        return ResponseEntity.ok(resultDTO);
    }

    public record AssociarEgressoInput(Long egressoId, Long cursoId, Long anoInicio, Long anoFim) {}
}
