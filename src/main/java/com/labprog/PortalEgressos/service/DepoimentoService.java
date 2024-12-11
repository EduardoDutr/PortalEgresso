package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DepoimentoService {

    private final DepoimentoRepository repository;

    public DepoimentoService(DepoimentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Depoimento salvar(Depoimento depoimento) {
        return repository.save(depoimento);
    }

    @Transactional
    public void delete(Depoimento depoimento) {
        repository.delete(depoimento);
    }

    public Set<Depoimento> obterPorEgresso(Long egressoId) {
        return repository.findAllByEgressoId(egressoId);
    }
}
