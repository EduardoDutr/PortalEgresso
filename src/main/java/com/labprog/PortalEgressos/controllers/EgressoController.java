package com.labprog.PortalEgressos.controllers;

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
            return new ResponseEntity(egresso, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/obterTodos")
    public ResponseEntity obterTodosEgressos(){
        try {
            List<Egresso> egressos = egressoService.obterTodos();
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorAno/{ano}")
    public ResponseEntity obterPorAno(@PathVariable Integer ano){
        try {
            Set<Egresso> egressos = egressoService.obterPorAno(ano.longValue());
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorCargo")
    public ResponseEntity obterPorCargo(@RequestBody Cargo cargo){
        try {
            Egresso egresso = egressoService.obterPorCargo(cargo); // TODO: Acho que deveria ser uma lista
            return new ResponseEntity(egresso, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/obterPorCurso")
    public ResponseEntity obterPorCurso(@RequestBody Curso curso){
        try {
            Set<Egresso> egressos = egressoService.obterPorCurso(curso);
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody Egresso egresso){
        try {
            Egresso salvo = egressoService.salvar(egresso);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
