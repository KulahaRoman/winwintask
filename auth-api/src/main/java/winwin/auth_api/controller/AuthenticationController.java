package winwin.auth_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winwin.auth_api.dto.CredentialsDTO;
import winwin.auth_api.dto.TokenDTO;
import winwin.auth_api.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO credentials) {
        return new ResponseEntity<>(authenticationService.login(credentials), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CredentialsDTO credentials) {
        authenticationService.register(credentials);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
