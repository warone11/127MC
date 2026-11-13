package com.warone11.auth.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        byte[] token = new byte[32];
        RANDOM.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }
    
    
}
