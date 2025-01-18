package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.CoordenadorDTO;
import com.labprog.PortalEgressos.DTO.CursoDTO;
import com.labprog.PortalEgressos.models.Coordenador;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.service.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/coordenador")
public class CoordenadorController {

    @Autowired
    CoordenadorService service;

    @PostMapping
    @RequestMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody CoordenadorDTO coordenadorDTO){
        try {
            Coordenador coordenador = Coordenador.builder()
                    .login(coordenadorDTO.getLogin())
                    .senha(coordenadorDTO.getSenha())
                    .build();
            service.salvar(coordenador);
            return ResponseEntity.ok(coordenador);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @RequestMapping(value = "/autenticar")
    public ResponseEntity autenticar(@RequestBody CoordenadorDTO coordenadorDTO){
        try{
            Coordenador coordenador = Coordenador.builder()
                    .login(coordenadorDTO.getLogin())
                    .senha(coordenadorDTO.getSenha())
                    .build();
            service.efetuarLogin(coordenador);
            return ResponseEntity.ok(HttpEntity.EMPTY);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping(value = "/obterCursos/{coordenadorId}")
    public ResponseEntity obterCursos(@PathVariable Long coordenadorId){
        try{
            List<Curso> cursos = service.obterCursos(coordenadorId);
            List<CursoDTO> cursosDTO = cursos.stream()
                    .map(CursoDTO::new)
                    .toList();
            return ResponseEntity.ok(cursosDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
