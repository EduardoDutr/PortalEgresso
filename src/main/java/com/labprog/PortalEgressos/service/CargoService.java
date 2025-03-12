package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidCargoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;
    @Autowired
    private EgressoRepository egressoRepository;
    @Autowired
    private UserProvider userProvider;

    @Transactional
    public Cargo criar(Cargo cargo, Long egressoId){
        validateUserAuthenticated();
        validar(cargo);
        Egresso egresso = egressoRepository.findById(egressoId).orElseThrow();

        cargo.setEgresso(egresso);
        egresso.add(cargo);
        return repository.save(cargo);
    }

    @Transactional
    public void deletar(Long cargoId) {
        validateUserAuthenticated();
        repository.deleteById(cargoId);
    }

    public Set<Cargo> obterPorEgresso(Long egressoId) {
        return repository.findAllByActiveEgressoId(egressoId);
    }

    private void validateUserAuthenticated() {
        if (!userProvider.userIsAdmin()) {
            throw new AuthorizationException();
        }
    }

    private void validar(Cargo cargo) {
        if (cargo == null) {
            throw new InvalidCargoException("O cargo não pode ser nulo.");
        }
        if (cargo.getDescricao() == null) {
            throw new InvalidCargoException("A descrição do cargo não pode ser nula.");
        }
        if (cargo.getLocal() == null) {
            throw new InvalidCargoException("O local do cargo não pode ser nulo.");
        }
        if (cargo.getAnoInicio() == null) {
            throw new InvalidCargoException("O ano de início do cargo não pode ser nulo.");
        }
    }


}
