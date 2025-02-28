package stellunia.StorageApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.StorageUserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final StorageUserRepository storageUserRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication potentialOAuth2Authentication = SecurityContextHolder.getContext().getAuthentication();
        if (potentialOAuth2Authentication != null) {
            if (potentialOAuth2Authentication instanceof OAuth2AuthenticationToken oauth2Token) {
                OAuth2User oAuth2User = oauth2Token.getPrincipal();

                String oidcId = oAuth2User.getName();

                Optional<StorageUser> potentialUser = storageUserRepository.findByOidcId(oidcId);
                if (potentialUser.isEmpty()) {
                    response.sendError(401, "Invalid token.");
                    return;
                }

                StorageUser storageUser = potentialUser.get();

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        storageUser, storageUser.getPassword(), storageUser.getAuthorities()));
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        UUID userId;
        try {
            userId = jwtService.validateToken(authHeader);
        } catch (Exception exception) {
            response.sendError(401, "Invalid token.");
            return;
        }

        Optional<StorageUser> potentialUser = storageUserRepository.findById(userId);
        if (potentialUser.isEmpty()) {
            response.sendError(401, "Invalid token.");
            return;
        }

        StorageUser user = potentialUser.get();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()
        ));
        filterChain.doFilter(request, response);
    }
}
