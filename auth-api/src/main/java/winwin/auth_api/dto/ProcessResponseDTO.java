package winwin.auth_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessResponseDTO {
    @JsonProperty("result")
    private final String text;
}
