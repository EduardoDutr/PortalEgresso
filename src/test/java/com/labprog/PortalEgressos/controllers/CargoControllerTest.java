package com.labprog.PortalEgressos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labprog.PortalEgressos.DTO.CargoDTO;
import com.labprog.PortalEgressos.models.Cargo;
import com.labprog.PortalEgressos.models.Egresso;
import com.labprog.PortalEgressos.service.CargoService;
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

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CargoController.class)
@AutoConfigureMockMvc
public class CargoControllerTest {

    static final String API = "/cargo";

    @Autowired
    MockMvc mvc;

    @MockBean
    CargoService cargoService;

    @Test
    public void deveSalvarCargo() throws Exception{
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("a")
                .email("a")
                .build();

        CargoDTO cargoDTO = CargoDTO.builder()
                .descricao("teste")
                .local("slz")
                .anoInicio(2012L)
                .egressoId(egresso.getId())
                .build();

        Cargo cargo = Cargo.builder()
                .descricao("teste")
                .local("slz")
                .anoInicio(2012L)
                .egresso(egresso)
                .build();

        when(cargoService.criar(any(Cargo.class), any(Long.class))).thenReturn(cargo);

        String json = new ObjectMapper().writeValueAsString(cargoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API + "/salvar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveDeletarCargo() throws Exception{
        doNothing().when(cargoService).deletar(any(Long.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/deletar/1");
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveObterCargoPorEgressoId() throws Exception{
        Egresso egresso = Egresso.builder()
                .id(1L)
                .nome("a")
                .email("a")
                .build();

        Set<Cargo> cargoSet = new HashSet<>();

        Cargo cargo1 = Cargo.builder()
                .id(1L)
                .descricao("teste")
                .local("slz")
                .anoInicio(2012L)
                .egresso(egresso)
                .build();
        Cargo cargo2 = Cargo.builder()
                .id(2L)
                .descricao("teste")
                .local("slz")
                .anoInicio(2012L)
                .egresso(egresso)
                .build();

        cargoSet.add(cargo1);
        cargoSet.add(cargo2);

        when(cargoService.obterPorEgresso(any(Long.class))).thenReturn(cargoSet);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/obterPorEgressoId/1");

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
