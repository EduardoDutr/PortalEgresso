package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import com.labprog.PortalEgressos.repositories.CursoEgressoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EgressoService {

    @Autowired
    private EgressoRepository egressoRepository;
    @Autowired
    private CursoEgressoRepository cursoEgressoRepository;
    @Autowired
    private CargoRepository cargoRepository;

    @Transactional
    public Egresso salvar(Egresso egresso) {
        return egressoRepository.save(egresso);
    }

    @Transactional
    public void delete(Egresso egresso) {
        egressoRepository.delete(egresso);
    }

    public Egresso obterPorId(Long id) {
        return egressoRepository.findById(id).orElseThrow();
    }

    public Set<Egresso> obterPorCurso(Curso curso) {
        return cursoEgressoRepository.findByCursoId(curso.getId()).stream()
                .map(CursoEgresso::getEgresso)
                .collect(Collectors.toSet());
    }

    public Egresso obterPorCargo(Cargo cargo) {
        return cargoRepository.findById(cargo.getId())
                .map(Cargo::getEgresso)
                .orElseThrow();
    }

    public Set<Egresso> obterPorAno(Long ano) {
        return cursoEgressoRepository.findByAnoFim(ano).stream()
                .map(CursoEgresso::getEgresso)
                .collect(Collectors.toSet());
    }
}
