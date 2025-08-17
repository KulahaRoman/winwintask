package winwin.auth_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredentialsDTO {
    @JsonProperty("email")
    private final String email;

    @JsonProperty("password")
    private final String password;
}
