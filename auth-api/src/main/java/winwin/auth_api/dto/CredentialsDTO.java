package winwin.auth_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredentialsDTO {
    @Email
    @NotBlank
    @JsonProperty("email")
    private final String email;

    @NotBlank
    @JsonProperty("password")
    private final String password;
}
