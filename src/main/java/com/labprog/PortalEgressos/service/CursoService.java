package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EgressoRepository egressoRepository;

    public Curso obter(Long cursoId){
        return cursoRepository.findById(cursoId).orElseThrow();
    }

    public List<Curso> obterTodos(){
        return cursoRepository.findAll();
    }

    @Transactional
    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Transactional
    public void deletar(Long cursoId) {
        cursoRepository.deleteById(cursoId);
    }

    @Transactional
    public Curso associarEgresso(Long egressoId, Long cursoId){
        Curso curso = cursoRepository.findById(cursoId).orElseThrow();
        Egresso egresso = egressoRepository.findById(egressoId).orElseThrow();

        Integer year = Year.now().getValue();
        CursoEgresso cursoEgresso = CursoEgresso.builder()
                .curso(curso)
                .egresso(egresso)
                .anoInicio(year)
                .build();

        curso.getEgressos().add(cursoEgresso);
        return cursoRepository.save(curso);
    }

    public List<Curso> obterPorEgresso(Long egressoId) {
        return egressoRepository.findById(egressoId).orElseThrow().getCursos().stream()
                .map(CursoEgresso::getCurso)
                .toList();
    }
}
