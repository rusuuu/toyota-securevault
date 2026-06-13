package com.toyota.vault.secrets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/secrets")
public class SecretController {
    @Autowired
    private SecretRepository secretRepository;

    @GetMapping
    public List<Secret> getAllSecrets(@AuthenticationPrincipal Jwt jwt) {
        String owner = jwt.getClaimAsString("sub");
        return secretRepository.findByOwnerEmail(owner);
    }

    @PostMapping
    public Secret createSecret(@RequestBody Secret secret, @AuthenticationPrincipal Jwt jwt) {
        secret.setOwnerEmail(jwt.getClaimAsString("sub"));
        return secretRepository.save(secret);
    }
}