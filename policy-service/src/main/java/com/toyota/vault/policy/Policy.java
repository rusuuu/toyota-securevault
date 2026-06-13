package com.toyota.vault.policy;

import jakarta.persistence.*;

@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;
    private String resourcePattern;
    private String permission;

    public Policy() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getResourcePattern() { return resourcePattern; }
    public void setResourcePattern(String resourcePattern) { this.resourcePattern = resourcePattern; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
}