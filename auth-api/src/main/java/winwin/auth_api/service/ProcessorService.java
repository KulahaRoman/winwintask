package winwin.auth_api.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;
import winwin.auth_api.entity.Log;
import winwin.auth_api.repository.LogRepository;
import winwin.auth_api.security.JwtAuthentication;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ProcessorService {
    private final LogRepository logRepository;
    private final RestClient restClient;

    public ProcessorService(LogRepository logRepository, RestClient restClient) {
        this.logRepository = logRepository;
        this.restClient = restClient;
    }

    public ProcessResponseDTO process(ProcessRequestDTO processRequestDTO) {
        // we ensure that authentication exists as we passed all filters and it has appropriate type
        var authentication = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());
        if (!(authentication instanceof JwtAuthentication)) {
            throw new RuntimeException("invalid authentication token");
        }

        var responseDTO = Objects.requireNonNull(restClient
                .post()
                .body(processRequestDTO)
                .retrieve()
                .body(ProcessRequestDTO.class));

        var log = new Log();
        log.setUserID(UUID.fromString((String) authentication.getPrincipal()));
        log.setInputText(processRequestDTO.getText());
        log.setOutputText(responseDTO.getText());
        log.setCreatedAt(System.currentTimeMillis());

        logRepository.save(log);
        
        return new ProcessResponseDTO(responseDTO.getText());
    }
}
