package winwin.auth_api.service;

import winwin.auth_api.dto.ProcessRequestDTO;
import winwin.auth_api.dto.ProcessResponseDTO;

public interface TransformationService {
    ProcessResponseDTO transformDTO(ProcessRequestDTO processRequestDTO);
}
