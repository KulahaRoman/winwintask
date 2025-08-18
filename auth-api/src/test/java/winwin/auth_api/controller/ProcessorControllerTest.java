package winwin.auth_api.controller;

import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;
import winwin.auth_api.security.JwtAuthentication;
import winwin.auth_api.service.TransformationService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProcessorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransformationService transformationService;

    @Test
    public void invokeProcessEndpoint_withoutAuthentication_403() throws Exception {
        mockMvc.perform(post("/api/process"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void invokeProcessEndpoint_withAuthenticationWithInvalidBody_400() throws Exception {
        var body = new JSONObject();
        body.put("invalid", "claim");

        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString())
                        .with(authentication(new JwtAuthentication("c572685c-36b1-4fa4-946a-661a0eac963b", true))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeProcessEndpoint_withAuthenticationWithValidBody_200() throws Exception {
        var body = new JSONObject();
        body.put("text", "hello");

        when(transformationService.transformDTO(Mockito.any(ProcessRequestDTO.class)))
                .thenReturn(new ProcessResponseDTO("OLLEH"));

        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString())
                        .with(authentication(new JwtAuthentication("c572685c-36b1-4fa4-946a-661a0eac963b", true))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OLLEH"));
    }
}
