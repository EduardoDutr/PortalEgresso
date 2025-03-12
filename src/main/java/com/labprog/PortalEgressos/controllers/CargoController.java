package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.DTO.CargoDTO;
import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @PostMapping("/salvar")
    public ResponseEntity<CargoDTO> salvar(@RequestBody CargoDTO cargoDTO){
        Cargo cargo = Cargo.builder()
                .descricao(cargoDTO.getDescricao())
                .local(cargoDTO.getLocal())
                .anoInicio(cargoDTO.getAnoInicio())
                .anoFim(cargoDTO.getAnoFim())
                .build();
        Cargo salvo = cargoService.criar(cargo, cargoDTO.getEgressoId());
        CargoDTO salvoDTO = new CargoDTO(salvo);
        return ResponseEntity.ok(salvoDTO);
    }

    @DeleteMapping("/deletar/{cargoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long cargoId){
        cargoService.deletar(cargoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/obterPorEgressoId/{egressoId}")
    public ResponseEntity<List<CargoDTO>> obterPorEgressoId(@PathVariable Long egressoId){
        Set<Cargo> cargos = cargoService.obterPorEgresso(egressoId);
        List<CargoDTO> cargosDTO = cargos.stream()
                .map(CargoDTO::new)
                .toList();
        return ResponseEntity.ok(cargosDTO);
    }
}
