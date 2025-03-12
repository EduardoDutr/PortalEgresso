package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.CursoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.EgressoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.InvalidCursoException;
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

    @Autowired
    private UserProvider userProvider;

    public Curso obter(Long cursoId){
        return cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNotFoundException(cursoId));
    }

    public List<Curso> obterTodos(){
        return cursoRepository.findAll();
    }

    @Transactional
    public Curso salvar(Curso curso) {
        validateUserAuthenticated();
        validar(curso);
        return cursoRepository.save(curso);
    }

    @Transactional
    public void deletar(Long cursoId) {
        validateUserAuthenticated();
        cursoRepository.deleteById(cursoId);
    }

    @Transactional
    public Curso associarEgresso(Long egressoId, Long cursoId, Long anoInicio, Long anoFim) {
        validar(egressoId, cursoId, anoInicio, anoFim);
        validateUserAuthenticated();
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNotFoundException(cursoId));
        Egresso egresso = egressoRepository.findById(egressoId).orElseThrow(() -> new EgressoNotFoundException(egressoId));

        CursoEgresso cursoEgresso = CursoEgresso.builder()
                .curso(curso)
                .egresso(egresso)
                .anoInicio(anoInicio.intValue())
                .anoFim(anoFim.intValue())
                .build();

        curso.getEgressos().add(cursoEgresso);
        return cursoRepository.save(curso);
    }

    public List<Curso> obterPorEgresso(Long egressoId) {
        return egressoRepository.findById(egressoId).orElseThrow().getCursos().stream()
                .map(CursoEgresso::getCurso)
                .toList();
    }

    private void validateUserAuthenticated() {
        if (!userProvider.userIsAdmin()) {
            throw new AuthorizationException();
        }
    }

    private void validar(Long egressoId, Long cursoId, Long anoInicio, Long anoFim) {
        if (egressoId == null || cursoId == null || anoInicio == null || anoFim == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validar(Curso curso) {
        if (curso == null || curso.getNome() == null || curso.getNivel() == null) {
            throw new InvalidCursoException();
        }
    }
}
