package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CargoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidCargoException;
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
    private UserProvider userProvider;

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
                .local("oi")
                .anoInicio(1L)
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
        when(userProvider.userIsAdmin()).thenReturn(true);
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
    public void deveDeletarCargo() {
        doNothing().when(cargoRepository).deleteById(any(Long.class));
        when(userProvider.userIsAdmin()).thenReturn(true);

        cargoService.deletar(cargo.getId());

        verify(cargoRepository, times(1)).deleteById(cargo.getId());
    }

    @Test
    public void deveObterPorEgresso() {
        Set<Cargo> cargosSet = new HashSet<>();
        cargosSet.add(cargo);

        when(cargoRepository.findAllByActiveEgressoId(egresso.getId())).thenReturn(cargosSet);

        Set<Cargo> result = cargoService.obterPorEgresso(egresso.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(cargo));
    }
    @Test
    public void deveValidateUserAuthenticatedShouldThrowAuthorizationExceptionWhenUserIsNotAdmin() {
        when(userProvider.userIsAdmin()).thenReturn(false);

        assertThrows(AuthorizationException.class, () -> {
            cargoService.criar(cargo, 1L);
        });
    }

    @Test
    public void deveValidarCargoShouldThrowExceptionWhenCargoIsNull() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        assertThrows(InvalidCargoException.class, () -> {
            cargoService.criar(null, 1L);
        });
    }

    @Test
    public void deveValidarCargoShouldThrowExceptionWhenDescricaoIsNull() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        cargo.setDescricao(null);

        assertThrows(InvalidCargoException.class, () -> {
            cargoService.criar(cargo, 1L);
        });
    }

    @Test
    public void deveValidarCargoShouldThrowExceptionWhenLocalIsNull() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        cargo.setLocal(null);

        assertThrows(InvalidCargoException.class, () -> {
            cargoService.criar(cargo, 1L);
        });
    }

    @Test
    public void deveValidarCargoShouldThrowExceptionWhenAnoInicioIsNull() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        cargo.setAnoInicio(null);

        assertThrows(InvalidCargoException.class, () -> {
            cargoService.criar(cargo, 1L);
        });
    }
}
