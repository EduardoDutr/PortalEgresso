package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.repositories.OportunidadeRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidOportunidadeException;
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

    @Transactional
    public Oportunidade salvar(Oportunidade oportunidade) {
        validar(oportunidade);
        oportunidade.setStatus(userIsAuthenticated() ? ACTIVE : PENDING);
        return repository.save(oportunidade);
    }

    @Transactional
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

    private void validar(Oportunidade oportunidade) {
        if (oportunidade == null) {
            throw new InvalidOportunidadeException("A oportunidade não pode ser nula.");
        }
        if (oportunidade.getTitulo() == null) {
            throw new InvalidOportunidadeException("O título da oportunidade não pode ser nulo.");
        }
        if (oportunidade.getUrl() == null) {
            throw new InvalidOportunidadeException("A URL da oportunidade não pode ser nula.");
        }
    }

}
