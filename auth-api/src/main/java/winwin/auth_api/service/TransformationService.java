package winwin.auth_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;

import java.util.Objects;

@Service
public class TransformationService {
    private final RestClient restClient;

    public TransformationService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProcessResponseDTO transformDTO(ProcessRequestDTO processRequestDTO) {
        var response = Objects.requireNonNull(restClient
                .post()
                .body(processRequestDTO)
                .retrieve()
                .body(ProcessRequestDTO.class));

        return new ProcessResponseDTO(response.getText());
    }
}
