package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.CoordenadorDTO;
import com.labprog.PortalEgressos.models.Coordenador;
import com.labprog.PortalEgressos.models.Curso;
import com.labprog.PortalEgressos.service.CoordenadorService;
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
@WebMvcTest(controllers = CoordenadorController.class)
@AutoConfigureMockMvc
public class CoordenadorControllerTest {

    static final String API = "/coordenador";

    @Autowired
    MockMvc mvc;

    @MockBean
    CoordenadorService coordenadorService;

    @Test
    public void deveSalvarCoordenador() throws Exception{
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("login")
                .senha("senha")
                .build();

        Coordenador coordenador = Coordenador.builder()
                .id(1L)
                .login("login")
                .senha("senha")
                .build();

        when(coordenadorService.salvar(any(Coordenador.class))).thenReturn(coordenador);

        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/salvar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveAutenticar() throws Exception{
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("login")
                .senha("senha")
                .build();

        Coordenador coordenador = Coordenador.builder()
                .id(1L)
                .login("login")
                .senha("senha")
                .build();

        when(coordenadorService.efetuarLogin(any(Coordenador.class))).thenReturn(true);

        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/autenticar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterCursos() throws Exception{
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

        when(coordenadorService.obterCursos(any(Long.class))).thenReturn(cursos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterCursos/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
