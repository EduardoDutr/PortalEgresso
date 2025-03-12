package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.EgressoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.InvalidEgressoException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.labprog.PortalEgressos.models.Egresso.Status.ACTIVE;
import static com.labprog.PortalEgressos.models.Egresso.Status.PENDING;
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
    public void testSalvarEgressoComoActiveQuandoEOCoordenador() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.save(any(Egresso.class))).thenReturn(egresso);

        Egresso result = egressoService.salvar(egresso);

        verify(egressoRepository, times(1)).save(egresso);

        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals(ACTIVE, result.getStatus());
    }

    @Test
    @Transactional
    public void testSalvarEgressoComoPendingQuandoSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);
        when(egressoRepository.save(any(Egresso.class))).thenReturn(egresso);

        Egresso result = egressoService.salvar(egresso);

        verify(egressoRepository, times(1)).save(egresso);

        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals(PENDING, result.getStatus());
    }

    @Test
    @Transactional
    public void deveDeletarEgresso() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        doNothing().when(egressoRepository).deleteById(any(Long.class));

        egressoService.deletar(egresso.getId());

        verify(egressoRepository, times(1)).deleteById(egresso.getId());
    }

    @Test
    public void lancaExcecaoAoTentarDeletarUsuarioSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        AuthorizationException thrown = assertThrows(
                AuthorizationException.class,
                () -> egressoService.deletar(2L)
        );

        assertNotNull(thrown);
        assertEquals("Usuário não está autorizado", thrown.getMessage());
    }


    @Test
    public void deveObterPorId() {
        when(egressoRepository.findActiveById(1L)).thenReturn(Optional.of(egresso));

        Egresso result = egressoService.obterPorId(1L);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    public void deveObterPorCurso() {
        when(egressoRepository.findActiveByCursoId(1L)).thenReturn(Set.of(egresso));

        Set<Egresso> egressos = egressoService.obterPorCurso(curso.getId());

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }

    @Test
    public void deveObterPorAno() {
        when(egressoRepository.findActiveByAnoFim(2022L)).thenReturn(Set.of(egresso));

        Set<Egresso> egressos = egressoService.obterPorAno(2022L);

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }

    @Test
    public void lancaExcecaoSeEgressoENulo() {
        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(null)
        );

        assertNotNull(thrown);
        assertEquals("O egresso não pode ser nulo.", thrown.getMessage());
    }

    @Test
    public void lancaExcecaoSeNomeDoEgressoENulo() {
        egresso.setNome(null);

        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(egresso)
        );

        assertNotNull(thrown);
        assertEquals("O nome do egresso não pode ser nulo.", thrown.getMessage());
    }

    @Test
    public void lancaExcecaoSeEmailDoEgressoENulo() {
        egresso.setEmail(null);

        InvalidEgressoException thrown = assertThrows(
                InvalidEgressoException.class,
                () -> egressoService.salvar(egresso)
        );

        assertNotNull(thrown);
        assertEquals("O e-mail do egresso não pode ser nulo.", thrown.getMessage());
    }

    @Test
    public void deveObterEgressosAtivos() {
        when(egressoRepository.findByStatus(ACTIVE)).thenReturn(Collections.singletonList(egresso));

        List<Egresso> result = egressoService.ativos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
        verify(egressoRepository, times(1)).findByStatus(ACTIVE);
    }

    @Test
    public void deveObterEgressosPendentes() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findByStatus(PENDING)).thenReturn(Collections.singletonList(egresso));

        List<Egresso> result = egressoService.obterPendentes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
        verify(egressoRepository, times(1)).findByStatus(PENDING);
    }

    @Test
    public void lancaExcecaoAoTentarObterEgressosPendentesSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        AuthorizationException thrown = assertThrows(
                AuthorizationException.class,
                () -> egressoService.obterPendentes()
        );

        assertNotNull(thrown);
        verify(egressoRepository, never()).findByStatus(PENDING);
    }

    @Test
    public void deveAtivarUsuario() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egresso));

        egressoService.ativar(1L);

        assertEquals(ACTIVE, egresso.getStatus());
        verify(egressoRepository, times(1)).save(egresso);
    }

    @Test
    public void lancaExcecaoAoTentarAtivarUsuarioSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        AuthorizationException thrown = assertThrows(
                AuthorizationException.class,
                () -> egressoService.ativar(1L)
        );

        assertNotNull(thrown);
        verify(egressoRepository, never()).findById(any(Long.class));
        verify(egressoRepository, never()).save(any(Egresso.class));
    }

    @Test
    public void lancaExcecaoQuandoUsuarioNaoEEncontrado() {
        when(egressoRepository.findActiveById(1L)).thenReturn(Optional.empty());

        EgressoNotFoundException thrown = assertThrows(
                EgressoNotFoundException.class,
                () -> egressoService.obterPorId(1L)
        );

        assertNotNull(thrown);
        assertEquals("Egresso com ID 1 não encontrado", thrown.getMessage());
        verify(egressoRepository, times(1)).findActiveById(1L);
    }
}