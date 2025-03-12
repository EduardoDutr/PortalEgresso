package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.DepoimentoDTO;
import com.labprog.PortalEgressos.models.Depoimento;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.service.DepoimentoService;
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
@WebMvcTest(controllers = DepoimentoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DepoimentoControllerTest {

    static final String API = "/depoimento";

    @Autowired
    MockMvc mvc;

    @MockBean
    DepoimentoService depoimentoService;

    @Test
    public void deveSalvarDepoimento() throws Exception {
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("a")
                .email("a")
                .build();
        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("teste")
                .egressoId(1L)
                .build();

        Depoimento depoimento = Depoimento.builder()
                .texto("teste")
                .egresso(egresso)
                .build();

        when(depoimentoService.salvar(any(Depoimento.class), any(Long.class))).thenReturn(depoimento);

        String json = new ObjectMapper().writeValueAsString(depoimentoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/salvar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveDeletarDepoimento() throws Exception{
        doNothing().when(depoimentoService).delete(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/deletar/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deveObterPorEgressoId() throws Exception{
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("a")
                .email("a")
                .build();

        List<Depoimento> depoimentoList = new ArrayList<>();

        Depoimento depoimento1 = Depoimento.builder()
                .id(1L)
                .texto("teste")
                .egresso(egresso)
                .build();
        Depoimento depoimento2 = Depoimento.builder()
                .id(2L)
                .texto("teste")
                .egresso(egresso)
                .build();

        depoimentoList.add(depoimento1);
        depoimentoList.add(depoimento2);

        when(depoimentoService.obterPorEgresso(any(Long.class))).thenReturn(depoimentoList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorEgresso/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
