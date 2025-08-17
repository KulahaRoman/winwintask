package winwin.auth_api.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;

@Service
@Profile("test")
public class LocalTransformationService implements TransformationService {
    private String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    @Override
    public ProcessResponseDTO transformDTO(ProcessRequestDTO processRequestDTO) {
        return new ProcessResponseDTO(reverseString(processRequestDTO.getText()).toUpperCase());
    }
}
