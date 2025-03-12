package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.repositories.OportunidadeRepository;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.labprog.PortalEgressos.models.Oportunidade.Status.ACTIVE;
import static com.labprog.PortalEgressos.models.Oportunidade.Status.PENDING;


@Service
public class OportunidadeService {

    @Autowired
    public OportunidadeRepository repository;
    @Autowired
    private UserProvider userProvider;

    public List<Oportunidade> obterAtivos() {
        return repository.findByStatus(ACTIVE);
    }

    public List<Oportunidade> obterPendentes() {
        validateUserAuthenticated();
        return repository.findByStatus(PENDING);
    }

    public Oportunidade salvar(Oportunidade oportunidade) {
        oportunidade.setStatus(userIsAuthenticated() ? ACTIVE : PENDING);
        return repository.save(oportunidade);
    }

    public void ativar(Long oportunidadeId) {
        validateUserAuthenticated();
        Oportunidade oportunidade = repository.findById(oportunidadeId).orElseThrow();
        oportunidade.setStatus(ACTIVE);
        repository.save(oportunidade);
    }

    @Transactional
    public void deletar(Long egressoId) {
        validateUserAuthenticated();
        repository.deleteById(egressoId);
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
