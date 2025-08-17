package winwin.data_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class ServiceAuthenticationFilter extends OncePerRequestFilter {
    private final String TOKEN_HEADER = "X-Internal-Token";

    private final String serviceToken;

    public ServiceAuthenticationFilter(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(TOKEN_HEADER)).ifPresent(header -> {
            if (header.equals(serviceToken)) {
                var authentication = new ServiceAuthentication();

                var securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        });

        filterChain.doFilter(request, response);
    }
}
