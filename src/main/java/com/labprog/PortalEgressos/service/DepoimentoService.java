package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository repository;

    @Transactional
    public Depoimento salvar(Depoimento depoimento) {
        return repository.save(depoimento);
    }

    @Transactional
    public void delete(Depoimento depoimento) {
        repository.delete(depoimento);
    }

    public List<Depoimento> obterPorEgresso(Long egressoId) {
        return repository.findAllByEgressoId(egressoId);
    }
}
