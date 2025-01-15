package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CargoServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private CargoService cargoService;

    private Cargo cargo;
    private Long egressoId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cargo = Cargo.builder().id(1L).descricao("Desenvolvedor").build();
        egressoId = 1L;
    }

    @Test
    @Transactional
    public void testSalvarCargo() {
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargo);

        Cargo result = cargoService.salvar(cargo);

        verify(cargoRepository).save(any());
        assertNotNull(result);
        assertEquals("Desenvolvedor", result.getDescricao());
    }

    @Test
    @Transactional
    public void testAssociarEgresso(){
        Cargo car = Cargo.builder()
                .descricao("TesteTexto")
                .local("SLZ")
                .anoInicio(1L)
                .build();
        Egresso egr = Egresso.builder()
                .nome("Edu")
                .email("edu@edu.com")
                .build();

        when(cargoRepository.save(any(Cargo.class))).thenReturn(car);

        Cargo salvo = cargoService.associarEgresso(car, egr);

        assertNotNull(salvo);

        assertEquals(salvo.getEgresso().getNome(), egr.getNome());
        assertEquals(salvo.getEgresso().getEmail(), egr.getEmail());
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

        when(cargoRepository.findAllByEgressoId(egressoId)).thenReturn(cargosSet);

        Set<Cargo> result = cargoService.obterPorEgresso(egressoId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(cargo));
    }
}
