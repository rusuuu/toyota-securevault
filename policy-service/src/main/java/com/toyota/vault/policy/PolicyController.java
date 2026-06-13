package com.toyota.vault.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    @Autowired
    private PolicyRepository policyRepository;

    @GetMapping
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @GetMapping("/by-role/{role}")
    public List<Policy> getByRole(@PathVariable String role) {
        return policyRepository.findByRoleName(role);
    }

    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyRepository.save(policy);
    }
}