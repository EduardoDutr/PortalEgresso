package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.CursoDTO;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.service.CursoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CursoController.class)
@AutoConfigureMockMvc
public class CursoControllerTest {

    static final String API = "/curso";

    @Autowired
    MockMvc mvc;

    @MockBean
    CursoService cursoService;

    @Test
    public void deveSalvarCurso() throws Exception{
        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("teste")
                .nivel("teste")
                .build();

        Curso curso = Curso.builder()
                .id(1L)
                .nome("teste")
                .nivel("teste")
                .build();


        when(cursoService.salvar(any(Curso.class))).thenReturn(curso);

        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/salvar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterPorCursoId() throws Exception{
        Curso curso = Curso.builder()
                .id(1L)
                .nome("teste")
                .nivel("teste")
                .build();

        when(cursoService.obter(any(Long.class))).thenReturn(curso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obter/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveDeletarPorCursoId() throws Exception{
        doNothing().when(cursoService).deletar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/deletar/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveAssociarEgressoCurso() throws Exception{
        Curso curso = Curso.builder()
                .id(1L)
                .nome("teste")
                .nivel("teste")
                .build();

        when(cursoService.associarEgresso(any(Long.class), any(Long.class))).thenReturn(curso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/associar/1/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterPorEgressoId() throws Exception{
        List<Curso> cursos = new ArrayList<>();

        Curso curso1 = Curso.builder()
                .id(1L)
                .nome("teste")
                .nivel("teste")
                .build();
        Curso curso2 = Curso.builder()
                .id(2L)
                .nome("teste")
                .nivel("teste")
                .build();

        cursos.add(curso1);
        cursos.add(curso2);

        when(cursoService.obterPorEgresso(any(Long.class))).thenReturn(cursos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorEgresso/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
