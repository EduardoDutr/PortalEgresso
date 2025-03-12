package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
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

    @InjectMocks
    private DepoimentoService depoimentoService;

    private Egresso egresso;
    private Cargo cargo;
    private Curso curso;
    private CursoEgresso cursoEgresso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        egresso = new Egresso();
        egresso.setId(1L);
        egresso.setNome("João");

        cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setEgresso(egresso);

        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Engenharia de Software");

        cursoEgresso = new CursoEgresso();
        cursoEgresso.setId(1L);
        cursoEgresso.setCurso(curso);
        cursoEgresso.setEgresso(egresso);
        cursoEgresso.setAnoInicio(2018);
        cursoEgresso.setAnoFim(2022);
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
    public void testObterPorCargo() {
        when(egressoRepository.findActiveByCargoId(1L)).thenReturn(Optional.of(egresso));

        Egresso result = egressoService.obterPorCargo(cargo.getId());

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    public void testObterPorAno() {
        when(egressoRepository.findActiveByAnoFim(2022L)).thenReturn(Set.of(egresso));

        Set<Egresso> egressos = egressoService.obterPorAno(2022L);

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }
}

