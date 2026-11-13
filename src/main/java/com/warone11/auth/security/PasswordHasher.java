package com.warone11.auth.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHasher {
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 32 * 1024;
    private static final int PARALLELISM = 1;
    private final Argon2 argon2;

    public PasswordHasher() {
        this.argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id
        );
    }
    public String hash(String password) {
        if(password == null|| password.isEmpty())
            throw new IllegalArgumentException("password is null or empty");
        char[] passwordAsCharArray = password.toCharArray();
        try {
            String hash= argon2.hash(ITERATIONS, MEMORY, PARALLELISM, passwordAsCharArray);
            return hash;
        } finally{
            argon2.wipeArray(passwordAsCharArray);
        }
        

    }
    public boolean verify(String password, String hash) {
        if(password == null || hash == null)
            throw new IllegalArgumentException("password or hash is null");
        char[] passwordAsCharArray = password.toCharArray();
        try {
            return argon2.verify(hash, passwordAsCharArray);
        } finally {
            argon2.wipeArray(passwordAsCharArray);
        }
    }
}
