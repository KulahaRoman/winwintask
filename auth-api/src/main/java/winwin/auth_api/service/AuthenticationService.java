package winwin.auth_api.service;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import winwin.auth_api.dto.CredentialsDTO;
import winwin.auth_api.dto.TokenDTO;
import winwin.auth_api.entity.User;
import winwin.auth_api.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenDTO login(CredentialsDTO credentials) {
        // check if user exists
        var user = userRepository.findByEmail(credentials.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        // check if password matches
        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("incorrect password");
        }

        // create and return token
        var token = jwtService.generateToken(user);
        return new TokenDTO(token);
    }

    public void register(CredentialsDTO credentials) {
        // check if user exists
        userRepository.findByEmail(credentials.getEmail()).ifPresent(user -> {
            throw new RuntimeException("user already exists");
        });

        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));

        userRepository.save(user);
    }
}
