package winwin.auth_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import winwin.auth_api.service.JwtService;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String AUTH_TOKEN_HEADER = "Authorization";
    private final String AUTH_TOKEN_PREFIX = "Bearer ";

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(AUTH_TOKEN_HEADER)).ifPresent(header -> {
            if (header.startsWith(AUTH_TOKEN_PREFIX)) {
                var token = header.substring(AUTH_TOKEN_PREFIX.length());
                if (jwtService.isTokenValid(token)) {
                    var subject = jwtService.extractSubject(token);
                    var authentication = new JwtAuthentication(subject, true);
                    // if we would have roles in token - we would extract them ...
                    // ... and pass into constructor, but for now - List.of()
                    // and also it is very bad choice to visit database at each request to the service ...
                    // ... in terms of performance

                    var securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
        });

        filterChain.doFilter(request, response);
    }
}