package com.warone11.auth.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class TokenHasher {
    public static String hash(String token) {
        if(token==null||token.isEmpty())
           throw new IllegalArgumentException("token cannot be null or empty");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            String encodedHash = HexFormat.of().formatHex(hash);
            return encodedHash;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not found",e);
        }
        
        
    }
}