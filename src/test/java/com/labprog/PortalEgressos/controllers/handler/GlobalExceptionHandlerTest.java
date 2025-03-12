package com.labprog.PortalEgressos.controllers.handler;

import com.labprog.PortalEgressos.controllers.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = MockController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    public void testHandleAuthorizationException() throws Exception {
        mockMvc.perform(get("/dummy/authorization"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Usuário não está autorizado"));
    }

    @Test
    public void testHandleCursoNotFoundException() throws Exception {
        mockMvc.perform(get("/dummy/curso-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Curso com ID 1 não encontrado"));
    }

    @Test
    public void testHandleEgressoNotFoundException() throws Exception {
        mockMvc.perform(get("/dummy/egresso-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Egresso com ID 2 não encontrado"));
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/dummy/illegal-argument"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Argumento inválido."));
    }

    @Test
    public void testHandleRuntimeException() throws Exception {
        mockMvc.perform(get("/dummy/runtime-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Houve um erro inesperado ao processar sua solicitação."));
    }

}
