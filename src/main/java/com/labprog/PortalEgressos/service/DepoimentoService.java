package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository repository;

    @Autowired
    private EgressoRepository egressoRepository;

    @Transactional
    public Depoimento salvar(Depoimento depoimento, Long egressoId) {
        Egresso egresso = egressoRepository.findById(egressoId).orElseThrow();

        depoimento.setEgresso(egresso);
        if(egresso.getDepoimentos() == null){
            egresso.setDepoimentos(new ArrayList<>());
        }
        egresso.getDepoimentos().add(depoimento);
        return repository.save(depoimento);
    }

    @Transactional
    public void delete(Long depoimentoId) {
        repository.deleteById(depoimentoId);
    }

    public List<Depoimento> obterPorEgresso(Long egressoId) {
        return repository.findAllByEgressoId(egressoId);
    }
}
