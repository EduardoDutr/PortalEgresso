package com.labprog.PortalEgressos.repositories;

import com.labprog.PortalEgressos.models.Coordenador;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CoordenadorRepositoryTest {
    @Autowired
    CoordenadorRepository repository;

    @Test
    @Transactional
    public void deveSalvar(){

        Coordenador coordenador = Coordenador.builder()
                .login("eduardo@eduardo.com")
                .senha("senhaTeste")
                .build();

        Coordenador salvo = repository.save(coordenador);

        assertNotNull(salvo);

        assertEquals(coordenador.getLogin(), salvo.getLogin());
        assertEquals(coordenador.getSenha(), salvo.getSenha());
    }

    @Test
    @Transactional
    public void deveEncontrarPorLogin(){

        String login = "eduardo@eduardo.com";

        Coordenador coordenador = Coordenador.builder()
                .login(login)
                .senha("senhaTeste")
                .build();

        repository.save(coordenador);

        Coordenador salvo = repository.findByLogin(login).orElseThrow();

        assertNotNull(salvo);

        assertEquals(coordenador.getLogin(), salvo.getLogin());
        assertEquals(coordenador.getSenha(), salvo.getSenha());
    }
}
