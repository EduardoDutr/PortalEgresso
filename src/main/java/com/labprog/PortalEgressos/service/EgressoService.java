package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.models.Egresso.Status;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.labprog.PortalEgressos.models.Egresso.Status.ACTIVE;
import static com.labprog.PortalEgressos.models.Egresso.Status.PENDING;

@Service
public class EgressoService {

    @Autowired
    private EgressoRepository egressoRepository;
    @Autowired
    private UserProvider userProvider;

    public List<Egresso> ativos() {
        return egressoRepository.findByStatus(ACTIVE);
    }

    public List<Egresso> obterPendentes() {
        validateUserAuthenticated();
        return egressoRepository.findByStatus(PENDING);
    }

    @Transactional
    public void atualizarStatus(Long egressoId, Status status) {
        validateUserAuthenticated();
        Egresso egresso = egressoRepository.findById(egressoId).orElseThrow();
        egresso.setStatus(status);
        egressoRepository.save(egresso);
    }

    @Transactional
    public Egresso salvar(Egresso egresso) {
        egresso.setStatus(userIsAuthenticated() ? ACTIVE : PENDING);
        return egressoRepository.save(egresso);
    }

    @Transactional
    public void deletar(Long egressoId) {
        validateUserAuthenticated();
        egressoRepository.deleteById(egressoId);
    }

    public Egresso obterPorId(Long id) {
        return egressoRepository.findActiveById(id).orElseThrow();
    }

    public Set<Egresso> obterPorCurso(Long cursoId) {
        return egressoRepository.findActiveByCursoId(cursoId);
    }

    public Egresso obterPorCargo(Long cargoId) {
        return egressoRepository.findActiveByCargoId(cargoId).orElseThrow();
    }

    public Set<Egresso> obterPorAno(Long ano) {
        return egressoRepository.findActiveByAnoFim(ano);
    }

    private void validateUserAuthenticated() {
        if (!userProvider.userIsAdmin()) {
            throw new AuthorizationException();
        }
    }

    private boolean userIsAuthenticated() {
        return userProvider.userIsAdmin();
    }
}
