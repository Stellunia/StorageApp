package stellunia.StorageApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class JWTService {

    private static final Algorithm algorithm = Algorithm.HMAC256("supersecret");
    private static final JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

    public String generateToken(UUID userId) {
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(userId.toString())
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public UUID validateToken(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return UUID.fromString(jwt.getSubject());
    }
}
