package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.OportunidadeDTO;
import com.labprog.PortalEgressos.models.Oportunidade;
import com.labprog.PortalEgressos.service.OportunidadeService;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = OportunidadeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OportunidadeControllerTest {

    static final String API = "/oportunidade";

    @Autowired
    MockMvc mvc;

    @MockBean
    OportunidadeService oportunidadeService;

    @Test
    public void deveSalvarOportunidade() throws Exception {
        OportunidadeDTO oportunidadeDTO = OportunidadeDTO.builder()
                .descricao("Test Opportunity")
                .build();

        Oportunidade oportunidadeSalva = Oportunidade.builder()
                .id(1L)
                .descricao("Test Opportunity")
                .build();

        when(oportunidadeService.salvar(any(Oportunidade.class))).thenReturn(oportunidadeSalva);

        String json = new ObjectMapper().writeValueAsString(oportunidadeDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Test Opportunity"));
    }

    @Test
    public void deveDeletarOportunidade() throws Exception {
        doNothing().when(oportunidadeService).deletar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/1");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deveObterOportunidadesAtivas() throws Exception {
        List<Oportunidade> oportunidades = new ArrayList<>();
        oportunidades.add(Oportunidade.builder().id(1L).descricao("Opportunity 1").build());
        oportunidades.add(Oportunidade.builder().id(2L).descricao("Opportunity 2").build());

        when(oportunidadeService.obterAtivos()).thenReturn(oportunidades);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }

    @Test
    public void deveAtualizarStatusDaOportunidadeParaActive() throws Exception {
        doNothing().when(oportunidadeService).ativar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API + "/1/ACTIVE");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(oportunidadeService).ativar(any());
    }

    @Test
    public void deveAtualizarStatusDaOportunidadeParaRejected() throws Exception {
        doNothing().when(oportunidadeService).ativar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API + "/1/REJECTED");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(oportunidadeService).deletar(any());

    }

    @Test
    public void deveObterOportunidadesPendentes() throws Exception {
        List<Oportunidade> oportunidades = new ArrayList<>();
        oportunidades.add(Oportunidade.builder().id(1L).descricao("Pending Opportunity 1").build());
        oportunidades.add(Oportunidade.builder().id(2L).descricao("Pending Opportunity 2").build());

        when(oportunidadeService.obterPendentes()).thenReturn(oportunidades);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/pendentes");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }
}