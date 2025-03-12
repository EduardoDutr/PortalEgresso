package com.labprog.PortalEgressos.service;

import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.CursoEgresso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.repositories.CursoRepository;
import com.labprog.PortalEgressos.repositories.EgressoRepository;
import com.labprog.PortalEgressos.service.auth.UserProvider;
import com.labprog.PortalEgressos.service.exceptions.AuthorizationException;
import com.labprog.PortalEgressos.service.exceptions.CursoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.EgressoNotFoundException;
import com.labprog.PortalEgressos.service.exceptions.InvalidCursoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CursoServiceTest {

    @Mock
    private UserProvider userProvider;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private EgressoRepository egressoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso curso;
    private Egresso egresso;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Curso de TI");
        curso.setNivel("Bacharelado");

        egresso = new Egresso();
        egresso.setId(1L);
        egresso.setNome("João");
        egresso.setEmail("joao@exemplo.com");

        CursoEgresso cursoEgresso = new CursoEgresso();
        cursoEgresso.setCurso(curso);
        cursoEgresso.setEgresso(egresso);

        egresso.setCursos(Set.of(cursoEgresso));
    }

    @Test
    @Transactional
    public void deveSalvarCurso() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        Curso result = cursoService.salvar(curso);

        verify(cursoRepository, times(1)).save(curso);

        assertNotNull(result);
        assertEquals("Curso de TI", result.getNome());
    }

    @Test
    public void deveObterTodosCursosAtivos() {
        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<Curso> cursos = cursoService.obterTodos();

        assertNotNull(cursos);
        assertEquals(1, cursos.size());
        assertEquals("Curso de TI", cursos.get(0).getNome());

    }

    @Test
    @Transactional
    public void deveDeletarCurso() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        doNothing().when(cursoRepository).deleteById(any(Long.class));

        cursoService.deletar(curso.getId());

        verify(cursoRepository).deleteById(curso.getId());
    }

    @Test
    public void deveObterCursosPorEgresso() {
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egresso));

        List<Curso> cursos = cursoService.obterPorEgresso(1L);

        assertNotNull(cursos);
        assertEquals(1, cursos.size());
        assertEquals("Curso de TI", cursos.get(0).getNome());
    }

    @Test
    @Transactional
    public void deveAssociarCursoAoEgresso(){
        Curso cur = new Curso();
        cur.setId(1L);
        cur.setNome("Curso de TI");
        cur.setNivel("Bacharelado");

        Egresso egr = new Egresso();
        egr.setId(1L);
        egr.setNome("A");
        egr.setEmail("a@exemplo.com");

        when(userProvider.userIsAdmin()).thenReturn(true);

        when(cursoRepository.save(any(Curso.class))).thenReturn(cur);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cur));
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egr));

        var salvo = cursoService.associarEgresso(cur.getId(), egr.getId(), 2021L, 2025L);

        assertNotNull(salvo);

//        assertEquals(salvo.getEgressos().stream().toList().getFirst(), egr.getCursos().stream().toList().getFirst());
    }

    @Test
    public void deveLancarExcecaoQuandoCursoNaoForEncontrado() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(CursoNotFoundException.class, () -> cursoService.obter(2L));
    }

    @Test
    public void deveLancarExcecaoQuandoSalvarCursoNulo() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        assertThrows(InvalidCursoException.class, () -> cursoService.salvar(null));
    }

    @Test
    public void deveLancarExcecaoQuandoSalvarCursoSemNome() {
        curso.setNome(null);
        when(userProvider.userIsAdmin()).thenReturn(true);
        assertThrows(InvalidCursoException.class, () -> cursoService.salvar(curso));
    }

    @Test
    public void deveLancarExcecaoQuandoSalvarCursoSemNivel() {
        curso.setNivel(null);
        when(userProvider.userIsAdmin()).thenReturn(true);
        assertThrows(InvalidCursoException.class, () -> cursoService.salvar(curso));
    }

    @Test
    public void deveLancarExcecaoQuandoAssociarEgressoNaoExistente() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(2L)).thenReturn(Optional.empty());
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        assertThrows(EgressoNotFoundException.class, () -> cursoService.associarEgresso(2L, 1L, 2020L, 2024L));
    }

    @Test
    public void deveLancarExcecaoQuandoAssociarCursoNaoExistente() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());
        when(egressoRepository.findById(1L)).thenReturn(Optional.of(egresso));
        assertThrows(CursoNotFoundException.class, () -> cursoService.associarEgresso(1L, 2L, 2020L, 2024L));
    }

    @Test
    public void deveLancarExcecaoAoTentarAssociarSemAnoFim() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(2L)).thenReturn(Optional.of(egresso));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        assertThrows(IllegalArgumentException.class, () -> cursoService.associarEgresso(2L, 1L, 2020L, null));
    }

    @Test
    public void deveLancarExcecaoAoTentarAssociarSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> cursoService.associarEgresso(2L, 1L, 2020L, 2024L));
    }

    @Test
    public void deveLancarExcecaoAoTentarSalvarSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> cursoService.salvar(curso));
    }

    @Test
    public void deveLancarExcecaoAoTentarDeletarSemLogin() {
        when(userProvider.userIsAdmin()).thenReturn(false);
        assertThrows(AuthorizationException.class, () -> cursoService.associarEgresso(2L, 1L, 2020L, 2024L));
    }

    @Test
    public void deveLancarExcecaoAoTentarAssociarSemEgressoId() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(2L)).thenReturn(Optional.of(egresso));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        assertThrows(IllegalArgumentException.class, () -> cursoService.associarEgresso(null, 1L, 2020L, 2024L));
    }

    @Test
    public void deveLancarExcecaoAoTentarAssociarSemAnoInicio() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(2L)).thenReturn(Optional.of(egresso));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        assertThrows(IllegalArgumentException.class, () -> cursoService.associarEgresso(2L, 1L, null, 2024L));
    }

    @Test
    public void deveLancarExcecaoAoTentarAssociarSemCursoId() {
        when(userProvider.userIsAdmin()).thenReturn(true);
        when(egressoRepository.findById(2L)).thenReturn(Optional.of(egresso));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        assertThrows(IllegalArgumentException.class, () -> cursoService.associarEgresso(2L, null, 2020L, 2024L));
    }
}
