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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void invokeRegisterEndpoint_withoutBody_400() throws Exception {
        mockMvc.perform(post("/api/auth/register"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeRegisterEndpoint_withInvalidBody_400() throws Exception {
        var body = new JSONObject();
        body.put("username", "somename");
        body.put("password", "somepassword");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeRegisterEndpoint_withValidBody_201() throws Exception {
        var body = new JSONObject();
        body.put("email", "wdaw@gmail.com");
        body.put("password", "somepassword");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void invokeRegisterEndpoint_twoTimesSameBody_400() throws Exception {
        var body = new JSONObject();
        body.put("email", "wdaw@gmail.com");
        body.put("password", "somepassword");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeLoginEndpoint_withoutBody_400() throws Exception {
        mockMvc.perform(post("/api/auth/login"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeLoginEndpoint_withInvalidBody_400() throws Exception {
        var body = new JSONObject();
        body.put("username", "somename");
        body.put("password", "somepassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invokeLoginEndpoint_withValidBody_200() throws Exception {
        var body = new JSONObject();
        body.put("email", "wdaw@gmail.com");
        body.put("password", "somepassword");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}
