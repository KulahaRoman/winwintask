package winwin.data_api.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import winwin.data_api.security.ServiceAuthentication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransformationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void invokeTransformEndpoint_withoutAuthentication_403() throws Exception {
        mockMvc.perform(post("/api/transform"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void invokeTransformEndpoint_withAuthenticationWithValidBody_200() throws Exception {
        var body = new JSONObject();
        body.put("text", "hello");

        mockMvc.perform(post("/api/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString())
                        .with(authentication(new ServiceAuthentication())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("OLLEH"));
    }

    @Test
    public void invokeTransformEndpoint_withAuthenticationWithInvalidBody_400() throws Exception {
        var body = new JSONObject();
        body.put("invalid", "body");

        mockMvc.perform(post("/api/transform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString())
                        .with(authentication(new ServiceAuthentication())))
                .andExpect(status().isBadRequest());
    }
}
