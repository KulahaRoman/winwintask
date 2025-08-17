package winwin.auth_api;

import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import winwin.auth_api.security.JwtAuthentication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProcessorControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString())
                        .with(authentication(new JwtAuthentication("c572685c-36b1-4fa4-946a-661a0eac963b", true))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OLLEH"));
    }
}
