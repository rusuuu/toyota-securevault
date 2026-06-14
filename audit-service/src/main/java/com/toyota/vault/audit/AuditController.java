package com.toyota.vault.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private GeminiClient geminiClient;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @PostMapping
    public AuditLog createLog(@RequestBody AuditLog log, @AuthenticationPrincipal Jwt jwt) {
        log.setActor(jwt.getClaimAsString("sub"));
        return auditLogRepository.save(log);
    }

    // ---- AI Audit Summarizer ----
    @GetMapping("/ai-summary")
    public Map<String, String> aiSummary() {
        List<AuditLog> logs = auditLogRepository.findAll();

        if (logs.isEmpty()) {
            return Map.of("summary", "Nu există loguri de audit de analizat.");
        }

        String logsText = logs.stream()
                .map(l -> String.format("- [%s] actor=%s action=%s resource=%s",
                        l.getTimestamp(), l.getActor(), l.getAction(), l.getResource()))
                .collect(Collectors.joining("\n"));

        String prompt = """
                Ești un analist de securitate pentru un sistem de tip "vault de secrete" (Toyota SecureVault).
                Mai jos sunt jurnalele de audit (cine a făcut ce acțiune, asupra cărei resurse, când).
                Analizează-le și produ un rezumat scurt în limba română care să includă:
                1. Un sumar al activității (câte acțiuni, ce tipuri, ce actori).
                2. Eventuale tipare suspecte (ex: același actor cu multe acțiuni, accesări repetate, acțiuni sensibile precum ștergeri).
                3. O recomandare de securitate, dacă e cazul.
                Fii concis și profesional.

                Jurnale de audit:
                %s
                """.formatted(logsText);

        String summary = geminiClient.generateSummary(prompt);
        return Map.of("summary", summary);
    }
}