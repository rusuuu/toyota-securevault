package com.toyota.vault.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @PostMapping
    public AuditLog createLog(@RequestBody AuditLog log, @AuthenticationPrincipal Jwt jwt) {
        log.setActor(jwt.getClaimAsString("sub"));
        return auditLogRepository.save(log);
    }
}