package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Coordenador;
import com.labprog.PortalEgressos.repositories.CoordenadorRepository;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CoordenadorServiceTest {

    @Mock
    CoordenadorRepository coordenadorRepository;

    @Mock
    CursoRepository cursoRepository;

    @InjectMocks
    CoordenadorService service;

    String login = "teste@teste.com";
    String senha = "testar";

    Coordenador coordenador;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        coordenador = Coordenador.builder()
                .login(login)
                .senha(senha)
                .build();

    }

    @Test
    @Transactional
    public void deveSalvarCoordenador(){
        when(coordenadorRepository.save(any(Coordenador.class))).thenReturn(coordenador);

        Coordenador salvo = service.salvar(coordenador);

        verify(coordenadorRepository).save(any(Coordenador.class));

        assertNotNull(salvo);

        assertEquals(coordenador.getLogin(), salvo.getLogin());
        assertEquals(coordenador.getSenha(), salvo.getSenha());
    }

    @Test
    @Transactional
    public void deveEfetuarLogin(){
        when(coordenadorRepository.findByLogin(login)).thenReturn(Optional.of(coordenador));

        boolean result = service.efetuarLogin(coordenador);

        assertTrue(result);
    }
}
