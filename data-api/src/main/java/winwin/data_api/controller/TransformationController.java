package winwin.data_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winwin.data_api.dto.ProcessDTO;
import winwin.data_api.service.TransformationService;

@RestController
@RequestMapping("/api")
public class TransformationController {
    private final TransformationService transformationService;

    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @PostMapping("/transform")
    public ResponseEntity<ProcessDTO> transform(@RequestBody ProcessDTO processDTO) {
        return new ResponseEntity<>(transformationService.transform(processDTO), HttpStatus.OK);
    }
}
