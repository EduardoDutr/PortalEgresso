package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.CargoDTO;
import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/cargo")
public class CargoController {

    @Autowired
    CargoService cargoService;

    @PostMapping
    @RequestMapping(value = "/salvar")
    public ResponseEntity salvar(@RequestBody CargoDTO cargoDTO){
        try{
            Cargo cargo = Cargo.builder()
                    .descricao(cargoDTO.getDescricao())
                    .local(cargoDTO.getLocal())
                    .anoInicio(cargoDTO.getAnoInicio())
                    .anoFim(cargoDTO.getAnoFim())
                    .build();

            Cargo salvo = cargoService.criar(cargo, cargoDTO.getEgressoId());
            CargoDTO salvoDTO = new CargoDTO(salvo);

            return ResponseEntity.ok(salvoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    @RequestMapping(value = "/deletar/{cargoId}")
    public ResponseEntity deletar(@PathVariable Long cargoId){
        try{
            cargoService.deletar(cargoId);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping(value = "/obterPorEgressoId/{egressoId}")
    public ResponseEntity obterPorEgressoId(@PathVariable Long egressoId){
        try{
            Set<Cargo> cargos = cargoService.obterPorEgresso(egressoId);
            List<CargoDTO> cargosDTO = cargos.stream()
                    .map(CargoDTO::new)
                    .toList();
            return ResponseEntity.ok(cargosDTO);

        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
