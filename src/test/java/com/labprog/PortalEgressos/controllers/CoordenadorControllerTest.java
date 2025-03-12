package com.labprog.PortalEgressos.controllers;

import com.labprog.PortalEgressos.service.auth.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CoordenadorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CoordenadorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationService authenticationService;

    @Test
    public void deveAutenticarCoordenador() throws Exception {
        when(authenticationService.authenticate(any())).thenReturn("token-jwt-teste");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
                .header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("token-jwt-teste"));
    }
}