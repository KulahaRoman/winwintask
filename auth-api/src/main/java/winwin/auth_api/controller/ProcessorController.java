package winwin.auth_api.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;
import winwin.auth_api.service.ProcessorService;

@RestController
@RequestMapping("/api")
public class ProcessorController {
    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping("/process")
    public ProcessResponseDTO process(@Valid @RequestBody ProcessRequestDTO processRequestDTO) {
        return processorService.process(processRequestDTO);
    }
}
