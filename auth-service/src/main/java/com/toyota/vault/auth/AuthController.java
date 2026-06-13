package com.toyota.vault.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "user", jwt.getClaimAsString("sub"),
                "roles", jwt.getClaimAsStringList("roles") != null
                        ? jwt.getClaimAsStringList("roles")
                        : List.of(),
                "issuer", jwt.getClaimAsString("iss"),
                "expiresAt", jwt.getExpiresAt()
        );
    }
}