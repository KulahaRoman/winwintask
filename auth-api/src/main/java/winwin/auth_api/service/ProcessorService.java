package winwin.auth_api.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
    private final TransformationService transformationService;
    private final LogRepository logRepository;

    public ProcessorService(TransformationService transformationService, LogRepository logRepository) {
        this.logRepository = logRepository;
        this.transformationService = transformationService;
    }

    public ProcessResponseDTO process(ProcessRequestDTO processRequestDTO) {
        // we ensure that authentication exists as we passed all filters and it has appropriate type
        var authentication = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());
        if (!(authentication instanceof JwtAuthentication)) {
            throw new RuntimeException("invalid authentication token");
        }

        var responseDTO = transformationService.transformDTO(processRequestDTO);

        var log = new Log();
        log.setUserID(UUID.fromString((String) authentication.getPrincipal()));
        log.setInputText(processRequestDTO.getText());
        log.setOutputText(responseDTO.getText());
        log.setCreatedAt(System.currentTimeMillis());

        logRepository.save(log);

        return responseDTO;
    }
}
