package com.toyota.vault.secrets;
import jakarta.persistence.*;

@Entity
@Table(name = "secrets")
public class Secret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secretName;
    @Convert(converter = CryptoConverter.class)
    @Column(length = 512)
    private String secretValue;
    private String ownerEmail;

    public Secret() {}

    public Secret(String secretName, String secretValue, String ownerEmail) {
        this.secretName = secretName;
        this.secretValue = secretValue;
        this.ownerEmail = ownerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public String getSecretValue() {
        return secretValue;
    }

    public void setSecretValue(String secretValue) {
        this.secretValue = secretValue;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
