package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidEgressoException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EgressoServiceTest {

    @Mock
    private EgressoRepository egressoRepository;

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private EgressoService egressoService;

    private Egresso egresso;
    private Curso curso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        egresso = new Egresso();
        egresso.setId(1L);
        egresso.setEmail("AS");
        egresso.setNome("João");

        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Engenharia de Software");
    }

    @Test
    @Transactional
    public void testSalvarEgresso() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.save(any(Egresso.class))).thenReturn(egresso);

        Egresso result = egressoService.salvar(egresso);

        verify(egressoRepository, times(1)).save(egresso);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    @Transactional
    public void testDeletarEgresso() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        doNothing().when(egressoRepository).deleteById(any(Long.class));

        egressoService.deletar(egresso.getId());

        verify(egressoRepository, times(1)).deleteById(egresso.getId());
    }

    @Test
    public void testObterPorId() {
        when(egressoRepository.findActiveById(1L)).thenReturn(Optional.of(egresso));

        Egresso result = egressoService.obterPorId(1L);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    public void testObterPorCurso() {
        when(egressoRepository.findActiveByCursoId(1L)).thenReturn(Set.of(egresso));

        Set<Egresso> egressos = egressoService.obterPorCurso(curso.getId());

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }

    @Test
    public void testObterPorAno() {
        when(egressoRepository.findActiveByAnoFim(2022L)).thenReturn(Set.of(egresso));

        Set<Egresso> egressos = egressoService.obterPorAno(2022L);

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }

    @Test
    public void testValidateEgresso_WhenEgressoIsNull_ShouldThrowException() {
        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(null)
        );

        assertNotNull(thrown);
        assertEquals("O egresso não pode ser nulo.", thrown.getMessage());
    }

    @Test
    public void testValidateEgresso_WhenEgressoNomeIsNull_ShouldThrowException() {
        egresso.setNome(null);

        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(egresso)
        );

        assertNotNull(thrown);
        assertEquals("O nome do egresso não pode ser nulo.", thrown.getMessage());
    }

    @Test
    public void testValidateEgresso_WhenEgressoEmailIsNull_ShouldThrowException() {
        egresso.setEmail(null);

        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(egresso)
        );

        assertNotNull(thrown);
        assertEquals("O e-mail do egresso não pode ser nulo.", thrown.getMessage());
    }
}