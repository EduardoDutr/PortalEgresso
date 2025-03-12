package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.repositories.OportunidadeRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.InvalidOportunidadeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.labprog.PortalEgressos.models.Oportunidade.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OportunidadeServiceTest {

    @Mock
    private OportunidadeRepository repository;

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private OportunidadeService service;

    private Oportunidade oportunidade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        oportunidade = new Oportunidade();
        oportunidade.setId(1L);
        oportunidade.setTitulo("Nova Oportunidade");
        oportunidade.setUrl("http://oportunidade.com");
        oportunidade.setStatus(ACTIVE);
    }

    @Test
    public void testObterAtivos() {
        when(repository.findByStatus(ACTIVE)).thenReturn(List.of(oportunidade));

        List<Oportunidade> result = service.obterAtivos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Nova Oportunidade", result.get(0).getTitulo());
    }

    @Test
    public void testObterPendentes() {
        when(repository.findByStatus(PENDING)).thenReturn(List.of(oportunidade));
        when(userProvider.userIsAdmin()).thenReturn(true);

        List<Oportunidade> result = service.obterPendentes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Transactional
    public void testSalvar() {
        when(repository.save(any(Oportunidade.class))).thenReturn(oportunidade);
        when(userProvider.userIsAdmin()).thenReturn(true);

        Oportunidade result = service.salvar(oportunidade);

        verify(repository).save(any(Oportunidade.class));
        assertNotNull(result);
        assertEquals(ACTIVE, result.getStatus());
    }

    @Test
    @Transactional
    public void testAtivar() {
        when(repository.findById(1L)).thenReturn(Optional.of(oportunidade));
        when(userProvider.userIsAdmin()).thenReturn(true);

        service.ativar(1L);

        verify(repository).save(oportunidade);
        assertEquals(ACTIVE, oportunidade.getStatus());
    }

    @Test
    @Transactional
    public void testDeletar() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    public void testValidarOportunidadeNula() {
        Exception exception = assertThrows(InvalidOportunidadeException.class, () -> {
            service.salvar(null);
        });
        assertEquals("A oportunidade não pode ser nula.", exception.getMessage());
    }

    @Test
    public void testValidarTituloNulo() {
        oportunidade.setTitulo(null);
        Exception exception = assertThrows(InvalidOportunidadeException.class, () -> {
            service.salvar(oportunidade);
        });
        assertEquals("O título da oportunidade não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void testValidarUrlNula() {
        oportunidade.setUrl(null);
        Exception exception = assertThrows(InvalidOportunidadeException.class, () -> {
            service.salvar(oportunidade);
        });
        assertEquals("A URL da oportunidade não pode ser nula.", exception.getMessage());
    }

    @Test
    public void testValidarUsuarioNaoAutenticado() {
        when(userProvider.userIsAdmin()).thenReturn(false);
        Exception exception = assertThrows(AuthorizationException.class, () -> {
            service.obterPendentes();
        });
        assertNotNull(exception);
    }
}
