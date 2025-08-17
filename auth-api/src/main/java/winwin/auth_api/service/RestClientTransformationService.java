package winwin.auth_api.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;

import java.util.Objects;

@Service
@Profile({"dev", "prod"})
public class RestClientTransformationService implements TransformationService {
    private final RestClient restClient;

    public RestClientTransformationService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ProcessResponseDTO transformDTO(ProcessRequestDTO processRequestDTO) {
        var response = Objects.requireNonNull(restClient
                .post()
                .body(processRequestDTO)
                .retrieve()
                .body(ProcessRequestDTO.class));

        return new ProcessResponseDTO(response.getText());
    }
}
