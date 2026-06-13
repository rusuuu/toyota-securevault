package com.toyota.vault.secrets;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

    private static final String ALGO = "AES";
    // 16 bytes pentru AES-128. În producție: din variabilă de mediu / KMS, nu hardcodat.
    private final byte[] key = System.getenv()
            .getOrDefault("SECRETS_AES_KEY", "0123456789abcdef")
            .getBytes();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGO));
            return Base64.getEncoder().encodeToString(c.doFinal(attribute.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException("Encrypt failed", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGO));
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new IllegalStateException("Decrypt failed", e);
        }
    }
}