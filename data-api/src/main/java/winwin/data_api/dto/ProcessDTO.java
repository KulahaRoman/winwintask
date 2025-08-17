package winwin.data_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessDTO {
    @JsonProperty("text")
    private final String text;
}
