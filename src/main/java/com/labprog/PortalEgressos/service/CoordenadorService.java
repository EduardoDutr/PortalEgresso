package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Coordenador;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.repositories.CoordenadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {
    @Autowired
    CoordenadorRepository repository;

    @Transactional
    public Coordenador salvar(Coordenador coordenador){
        verificarCoordenador(coordenador);
        return repository.save(coordenador);
    }

    public boolean efetuarLogin(Coordenador coordenador){
        Optional<Coordenador> salvo = repository.findByLogin(coordenador.getLogin());

        if (salvo.isEmpty())
            throw new RuntimeException("Coordenador nao cadastrado");

        if (!salvo.get().getSenha().equals(coordenador.getSenha()))
            throw new RuntimeException("Senha incorreta");

        return true;
    }

    public List<Curso> obterCursos(Long coordenadorId){
        return repository.findById(coordenadorId).orElseThrow().getCursos();
    }

    private void verificarCoordenador(Coordenador coordenador){
        if (coordenador == null)
            throw new RuntimeException("Um coordenador valido deve ser informado");
        if (coordenador.getLogin() == null)
            throw new RuntimeException("Login do coordenador deve ser valido");
        if (repository.findByLogin(coordenador.getLogin()).isPresent())
            throw new RuntimeException("Coordenador ja existe");
        if (coordenador.getSenha() == null)
            throw new RuntimeException("O coordenador deve possui uma senha");
    }
}
