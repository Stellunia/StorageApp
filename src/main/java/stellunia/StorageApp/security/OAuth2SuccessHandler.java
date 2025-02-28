package stellunia.StorageApp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import stellunia.StorageApp.user.StorageUser;
import stellunia.StorageApp.user.StorageUserService;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final StorageUserService storageUserService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauth2Token.getPrincipal();

        System.out.println(oauth2Token.getAuthorizedClientRegistrationId());

        String oidcId = oAuth2User.getName();

        String username = oAuth2User.getAttribute("login");

        Optional<StorageUser> existingUser = storageUserService.findByOpenId(oidcId);

        if (existingUser.isEmpty()) {
            StorageUser storageUser = storageUserService.createOpenIdUser(username, oidcId);
            System.out.println("Registered user '" + storageUser.getUsername() + "' through OpenID-connect.");
        }
    }
}
