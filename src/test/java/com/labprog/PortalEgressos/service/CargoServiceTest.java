package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CargoServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private EgressoRepository egressoRepository;

    @InjectMocks
    private CargoService cargoService;

    private Cargo cargo;
    private Egresso egresso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cargo = Cargo.builder()
                .id(1L)
                .descricao("Desenvolvedor")
                .build();

        egresso = Egresso.builder()
                .id(1L)
                .nome("Teste")
                .email("Teste@Teste.com")
                .build();
    }

    @Test
    @Transactional
    public void deveSalvarCargo() {
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargo);
        when(egressoRepository.findById(egresso.getId())).thenReturn(Optional.of(egresso));

        Cargo result = cargoService.criar(cargo, egresso.getId());

        verify(cargoRepository).save(any(Cargo.class));

        assertNotNull(result);

        assertEquals("Desenvolvedor", result.getDescricao());

        assertEquals(result.getEgresso().getNome(), egresso.getNome());
        assertEquals(result.getEgresso().getEmail(), egresso.getEmail());
    }

    @Test
    @Transactional
    public void testDeletarCargo() {
        doNothing().when(cargoRepository).delete(any(Cargo.class));

        cargoService.deletar(cargo);

        verify(cargoRepository, times(1)).delete(cargo);
    }

    @Test
    public void testObterPorEgresso() {
        Set<Cargo> cargosSet = new HashSet<>();
        cargosSet.add(cargo);

        when(cargoRepository.findAllByEgressoId(egresso.getId())).thenReturn(cargosSet);

        Set<Cargo> result = cargoService.obterPorEgresso(egresso.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(cargo));
    }
}
