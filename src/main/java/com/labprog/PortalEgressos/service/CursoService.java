package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EgressoRepository egressoRepository;

    @Transactional
    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Transactional
    public void deletar(Curso curso) {
        cursoRepository.delete(curso);
    }

    public List<Curso> obterPorEgresso(Long egressoId) {
        return egressoRepository.findById(egressoId).orElseThrow().getCursos().stream()
                .map(CursoEgresso::getCurso)
                .toList();
    }
}
