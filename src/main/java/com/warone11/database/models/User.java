package com.warone11.database.models;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Defines a user of the application
 * User
 */
public class User {
    private UUID uuid;
    private String username;
    private String passwordHash;
    private LocalDateTime created_at;
    /**
     * Creates a new user
     * @param uuid uuid of the user
     * @param username username of the user, must be unique
     * @param passwordHash passwordHash of the user, can be null
     * @param created_at timestamp when the user was created
     * @throws IllegalArgumentException if uuid or username are null
     */
    public User(UUID uuid, String username, String passwordHash, LocalDateTime created_at) {
        if(uuid==null)
            throw new IllegalArgumentException("field uuid cannot be null");
        if(username==null)
            throw new IllegalArgumentException("UUID = "+uuid+": Field username cannot be null");
        this.uuid = uuid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.created_at = created_at;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        if(username==null)
        throw new IllegalArgumentException("UUID = "+uuid+": Field username cannot be null");
        this.username = username;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", username='" + getUsername() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            "}";
    }

}
