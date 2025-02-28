package stellunia.StorageApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import stellunia.StorageApp.user.StorageUserRepository;
import stellunia.StorageApp.user.StorageUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JWTService jwtService,
            StorageUserRepository storageUserRepository,
            StorageUserService storageUserService,
            OAuth2SuccessHandler oAuth2SuccessHandler
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(storageUserService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/blog-post").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/blog-post/like/**", "/blog-post/dislike/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/comment").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/admin/delete-post").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth -> {
                    oauth.successHandler(oAuth2SuccessHandler);
                })
                .addFilterBefore(new AuthenticationFilter(jwtService, storageUserRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
