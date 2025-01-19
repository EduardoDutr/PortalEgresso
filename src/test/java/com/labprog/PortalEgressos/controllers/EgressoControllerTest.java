package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.EgressoDTO;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.service.EgressoService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = EgressoController.class)
@AutoConfigureMockMvc
public class EgressoControllerTest {
    static final String API = "/egresso";

    @Autowired
    MockMvc mvc;

    @MockBean
    EgressoService egressoService;

    @Test
    public void deveSalvarEgresso() throws Exception {
        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        when(egressoService.salvar(any(Egresso.class))).thenReturn(egresso);

        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/salvar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterTodosEgressos() throws Exception{
        List<Egresso> egressoList = new ArrayList<>();
        Egresso egresso1 = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        Egresso egresso2 = Egresso.builder()
                .id(2L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        egressoList.add(egresso1);
        egressoList.add(egresso2);

        when(egressoService.obterTodos()).thenReturn(egressoList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterTodos");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterPorEgressoId() throws Exception{
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        when(egressoService.obterPorId(any(Long.class))).thenReturn(egresso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obter/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void deveObterPorAno() throws Exception{
        Set<Egresso> egressoSet = new HashSet<>();
        Egresso egresso1 = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        Egresso egresso2 = Egresso.builder()
                .id(2L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        egressoSet.add(egresso1);
        egressoSet.add(egresso2);

        when(egressoService.obterPorAno(any(Long.class))).thenReturn(egressoSet);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorAno/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterPorCargoId() throws Exception{
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        when(egressoService.obterPorCargo(any(Long.class))).thenReturn(egresso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorCargoId/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterPorCursoId() throws Exception{
        Set<Egresso> egressoSet = new HashSet<>();
        Egresso egresso1 = Egresso.builder()
                .id(1L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        Egresso egresso2 = Egresso.builder()
                .id(2L)
                .nome("NomeTeste")
                .email("NomeTeste@email.com")
                .build();

        egressoSet.add(egresso1);
        egressoSet.add(egresso2);

        when(egressoService.obterPorCurso(any(Long.class))).thenReturn(egressoSet);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorCursoId/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveDeletarPorEgressoId() throws Exception{
        doNothing().when(egressoService).deletar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/deletar/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
