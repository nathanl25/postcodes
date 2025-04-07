package io.nology.postcode_backend.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

// import com.nimbusds.jwt.JWTClaimsSet;

@Service
public class AuthService {
    private final JwtEncoder encoder;

    public AuthService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication auth) {

        Instant now = Instant.now();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(auth.getName())
                .claim("scope", scope)
                .build();
        System.out.println(scope);
        System.out.println(JwtEncoderParameters.from(claims).getClaims().getClaimAsString("scope"));
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
