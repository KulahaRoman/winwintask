package winwin.auth_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessResponseDTO {
    @NotBlank
    @JsonProperty("result")
    private final String text;
}
