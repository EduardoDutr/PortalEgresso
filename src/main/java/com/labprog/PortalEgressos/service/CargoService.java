package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CargoService {
    private final CargoRepository repository;

    public CargoService(CargoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cargo salvar(Cargo Cargo) {
        return repository.save(Cargo);
    }

    @Transactional
    public void deletar(Cargo cargo) {
        repository.delete(cargo);
    }

    public Set<Cargo> obterPorEgresso(Long egressoId) {
        return repository.findAllByEgressoId(egressoId);
    }
}
