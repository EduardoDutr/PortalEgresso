package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.*;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import com.labprog.PortalEgressos.repositories.CursoEgressoRepository;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EgressoServiceTest {

    @Mock
    private EgressoRepository egressoRepository;

    @Mock
    private CursoEgressoRepository cursoEgressoRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private DepoimentoRepository depoimentoRepository;

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
        when(egressoRepository.save(any(Egresso.class))).thenReturn(egresso);

        Egresso result = egressoService.salvar(egresso);

        verify(egressoRepository, times(1)).save(egresso);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    @Transactional
    public void testDeletarEgresso() {
        doNothing().when(egressoRepository).deleteById(any(Long.class));

        egressoService.deletar(egresso.getId());

        verify(egressoRepository, times(1)).deleteById(egresso.getId());
    }

    @Test
    public void testObterPorId() {
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egresso));

        Egresso result = egressoService.obterPorId(1L);

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    public void testObterPorCurso() {
        when(cursoEgressoRepository.findByCursoId(1L)).thenReturn(Collections.singletonList(cursoEgresso));

        Set<Egresso> egressos = egressoService.obterPorCurso(curso.getId());

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }

    @Test
    public void testObterPorCargo() {
        when(cargoRepository.findById(1L)).thenReturn(Optional.of(cargo));

        Egresso result = egressoService.obterPorCargo(cargo.getId());

        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    public void testObterPorAno() {
        when(cursoEgressoRepository.findByAnoFim(2022L)).thenReturn(Collections.singletonList(cursoEgresso));

        Set<Egresso> egressos = egressoService.obterPorAno(2022L);

        assertNotNull(egressos);
        assertEquals(1, egressos.size());
        assertTrue(egressos.stream().anyMatch(e -> e.getNome().equals("João")));
    }
    @Test
    public void testRecuperarDepoimentos(){
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egresso));

        Depoimento depo1 = Depoimento.builder()
                .texto("Teste1")
                .build();
        Depoimento depo2 = Depoimento.builder()
                .texto("Teste2")
                .build();
        Depoimento depo3 = Depoimento.builder()
                .texto("Teste3")
                .build();

        depoimentoService.salvar(depo1, egresso);
        depoimentoService.salvar(depo2, egresso);
        depoimentoService.salvar(depo3, egresso);

        var salvo = egressoRepository.findById(1L);

        assertNotNull(salvo);

        List<Depoimento> depoimentos = salvo.orElseThrow().getDepoimentos();

        assertEquals(3, depoimentos.size());

        assertTrue(depoimentos.stream().anyMatch(d -> d.getTexto().equals("Teste1")));
        assertTrue(depoimentos.stream().anyMatch(d -> d.getTexto().equals("Teste2")));
        assertTrue(depoimentos.stream().anyMatch(d -> d.getTexto().equals("Teste3")));
    }
}

