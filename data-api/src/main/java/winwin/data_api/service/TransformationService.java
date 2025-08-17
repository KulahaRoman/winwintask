package winwin.data_api.service;

import org.springframework.stereotype.Service;
import winwin.data_api.dto.ProcessDTO;

@Service
public class TransformationService {
    private String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    public ProcessDTO transform(ProcessDTO processDTO) {
        return new ProcessDTO(reverseString(processDTO.getText()).toUpperCase());
    }
}
