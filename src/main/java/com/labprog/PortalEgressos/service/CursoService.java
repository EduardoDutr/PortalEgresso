package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CursoService {
    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Curso salvar(Curso Curso) {
        return repository.save(Curso);
    }

    @Transactional
    public void deletar(Curso curso) {
        repository.delete(curso);
    }

    public Set<Curso> obterPorEgresso(Long egressoId) {
        return repository.findAllByEgressoId(egressoId);
    }
}
