package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.DepoimentoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidDepoimentoException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DepoimentoServiceTest {

    @Mock
    private UserProvider userProvider;
    @Mock
    private DepoimentoRepository depoimentoRepository;
    @Mock
    private EgressoRepository egressoRepository;

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
    public void deveSalvarDepoimento() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(depoimentoRepository.save(any(Depoimento.class))).thenReturn(depoimento);
        when(egressoRepository.findActiveById(egresso.getId())).thenReturn(Optional.of(egresso));

        Depoimento result = depoimentoService.salvar(depoimento, egresso.getId());

        verify(depoimentoRepository, times(1)).save(depoimento);

        assertNotNull(result);
        assertEquals("Excelente curso!", result.getTexto());
    }

    @Test
    public void lancaExcecaoAoTentarSalvarDepoimentoSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        assertThrows(
                AuthorizationException.class,
                () -> depoimentoService.salvar(depoimento, egresso.getId())
        );

        verify(depoimentoRepository, never()).save(depoimento);
    }

    @Test
    public void lancaExcecaoAoTentarSalvarDepoimentoNulo() {
        when(userProvider.userIsAdmin()).thenReturn(true);

        assertThrows(
                InvalidDepoimentoException.class,
                () -> depoimentoService.salvar(null, egresso.getId())
        );

        verify(depoimentoRepository, never()).save(depoimento);
    }

    @Test
    public void lancaExcecaoAoTentarSalvarDepoimentoComTextoNulo() {
        depoimento.setTexto(null);
        when(userProvider.userIsAdmin()).thenReturn(true);

        assertThrows(
                InvalidDepoimentoException.class,
                () -> depoimentoService.salvar(depoimento, egresso.getId())
        );

        verify(depoimentoRepository, never()).save(depoimento);
    }


    @Test
    @Transactional
    public void deveDeletarDepoimento() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        doNothing().when(depoimentoRepository).deleteById(any(Long.class));

        depoimentoService.delete(depoimento.getId());

        verify(depoimentoRepository, times(1)).deleteById(depoimento.getId());
    }

    @Test
    public void lancaExcecaoAoTentarDeletarDepoimentoSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        assertThrows(
                AuthorizationException.class,
                () -> depoimentoService.delete(depoimento.getId())
        );

        verify(depoimentoRepository, never()).deleteById(depoimento.getId());
    }

    @Test
    public void deveObterDepoimentosPorEgresso() {
        when(depoimentoRepository.findAllByActiveEgressoId(1L)).thenReturn(Arrays.asList(depoimento));

        List<Depoimento> depoimentos = depoimentoService.obterPorEgresso(1L);

        assertNotNull(depoimentos);
        assertEquals(1, depoimentos.size());
        assertEquals("Excelente curso!", depoimentos.get(0).getTexto());
    }
    @Test
    public void deveAssociarEgresso(){
        when(userProvider.userIsAdmin()).thenReturn(true);

        Depoimento depo = Depoimento.builder()
                .texto("TesteTexto")
                .build();
        Egresso egr = Egresso.builder()
                .nome("Edu")
                .email("edu@edu.com")
                .build();

        when(depoimentoRepository.save(any(Depoimento.class))).thenReturn(depo);
        when(egressoRepository.findActiveById(egr.getId())).thenReturn(Optional.of(egr));

        Depoimento salvo = depoimentoService.salvar(depo, egr.getId());

        assertNotNull(salvo);

        assertEquals(salvo.getEgresso().getNome(), egr.getNome());
        assertEquals(salvo.getEgresso().getEmail(), egr.getEmail());
    }
}

