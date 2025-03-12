package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.EgressoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.InvalidDepoimentoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository repository;

    @Autowired
    private EgressoRepository egressoRepository;

    @Autowired
    private UserProvider userProvider;

    @Transactional
    public Depoimento salvar(Depoimento depoimento, Long egressoId) {
        validateUserAuthenticated();
        validar(depoimento);
        depoimento.setData(new Date());
        Egresso egresso = egressoRepository.findActiveById(egressoId).orElseThrow(() -> new EgressoNotFoundException(egressoId));
        depoimento.setEgresso(egresso);
        egresso.add(depoimento);
        return repository.save(depoimento);
    }

    @Transactional
    public void delete(Long depoimentoId) {
        validateUserAuthenticated();
        repository.deleteById(depoimentoId);
    }

    public List<Depoimento> obterPorEgresso(Long egressoId) {
        return repository.findAllByActiveEgressoId(egressoId);
    }

    private void validateUserAuthenticated() {
        if (!userProvider.userIsAdmin()) {
            throw new AuthorizationException();
        }
    }

    private void validar(Depoimento depoimento) {
        if (depoimento == null) {
            throw new InvalidDepoimentoException("O depoimento não pode ser nulo.");
        }
        if (depoimento.getTexto() == null) {
            throw new InvalidDepoimentoException("O texto do depoimento não pode ser nulo.");
        }
    }

}
