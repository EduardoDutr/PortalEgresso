package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DepoimentoServiceTest {

    @Mock
    private DepoimentoRepository depoimentoRepository;

    @InjectMocks
    private DepoimentoService depoimentoService;

    private Depoimento depoimento;
    private Egresso egresso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        depoimento = new Depoimento();
        depoimento.setId(1L);
        depoimento.setTexto("Excelente curso!");

        egresso = new Egresso();
        egresso.setId(1L);
        egresso.setNome("João");

        depoimento.setEgresso(egresso);
    }

    @Test
    @Transactional
    public void testSalvarDepoimento() {
        when(depoimentoRepository.save(any(Depoimento.class))).thenReturn(depoimento);

        Depoimento result = depoimentoService.salvar(depoimento, egresso);

        verify(depoimentoRepository, times(1)).save(depoimento);

        assertNotNull(result);
        assertEquals("Excelente curso!", result.getTexto());
    }

    @Test
    @Transactional
    public void testDeletarDepoimento() {
        doNothing().when(depoimentoRepository).delete(any(Depoimento.class));

        depoimentoService.delete(depoimento);

        verify(depoimentoRepository, times(1)).delete(depoimento);
    }

    @Test
    public void testObterDepoimentosPorEgresso() {
        when(depoimentoRepository.findAllByEgressoId(1L)).thenReturn(Arrays.asList(depoimento));

        List<Depoimento> depoimentos = depoimentoService.obterPorEgresso(1L);

        assertNotNull(depoimentos);
        assertEquals(1, depoimentos.size());
        assertEquals("Excelente curso!", depoimentos.get(0).getTexto());
    }
    @Test
    public void testAssociarEgresso(){

        Depoimento depo = Depoimento.builder()
                .texto("TesteTexto")
                .build();
        Egresso egr = Egresso.builder()
                .nome("Edu")
                .email("edu@edu.com")
                .build();

        when(depoimentoRepository.save(any(Depoimento.class))).thenReturn(depo);

        Depoimento salvo = depoimentoService.salvar(depo, egr);

        assertNotNull(salvo);

        assertEquals(salvo.getEgresso().getNome(), egr.getNome());
        assertEquals(salvo.getEgresso().getEmail(), egr.getEmail());
    }
}

