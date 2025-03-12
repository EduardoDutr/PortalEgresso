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
        return egressoRepository.findById(egressoId).orElseThrow(() -> new EgressoNotFoundException(egressoId))
                .getCursos().stream()
                .map(CursoEgresso::getCurso)
                .toList();
    }

    private void validateUserAuthenticated() {
        if (!userProvider.userIsAdmin()) {
            throw new AuthorizationException();
        }
    }

    private void validar(Long egressoId, Long cursoId, Long anoInicio, Long anoFim) {
        if (egressoId == null) {
            throw new IllegalArgumentException("O ID do egresso não pode ser nulo.");
        }
        if (cursoId == null) {
            throw new IllegalArgumentException("O ID do curso não pode ser nulo.");
        }
        if (anoInicio == null) {
            throw new IllegalArgumentException("O ano de início do egresso no curso não pode ser nulo.");
        }
        if (anoFim == null) {
            throw new IllegalArgumentException("O ano de fim do egresso no curso não pode ser nulo.");
        }
    }


    private void validar(Curso curso) {
        if (curso == null) {
            throw new InvalidCursoException("O curso não pode ser nulo.");
        }
        if (curso.getNome() == null) {
            throw new InvalidCursoException("O nome do curso não pode ser nulo.");
        }
        if (curso.getNivel() == null) {
            throw new InvalidCursoException("O nível do curso não pode ser nulo.");
        }
    }

}
