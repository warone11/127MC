package com.warone11.database.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.DatabaseManager;
/**
 * Defines a user of the application
 * User
 */
public class User {
    private UUID uuid;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;
    /**
     * Creates a new user
     * @param uuid uuid of the user
     * @param username username of the user, must be unique
     * @param passwordHash passwordHash of the user, can be null
     * @param created_at timestamp when the user was created
     * @throws IllegalArgumentException if uuid or username are null
     */
    public User(UUID uuid, String username, String passwordHash, LocalDateTime createdAt) {
        if(uuid==null)
            throw new IllegalArgumentException("field uuid cannot be null");
        if(username==null)
            throw new IllegalArgumentException("UUID = "+uuid+": Field username cannot be null");
        this.uuid = uuid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }
    public User(ResultSet rs) throws SQLException{
        this(UUID.fromString(rs.getString("uuid")),rs.getString("username"),rs.getString("pasword_hash"),rs.getString("created_at")==null?null:LocalDateTime.parse(rs.getString("created_at"), DatabaseManager.SQLITE_DATETIME_FORMAT));
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public String getCreatedAtAsSQL(){
        if(createdAt==null){
            return null;
        }
        else{
            return createdAt.format(DatabaseManager.SQLITE_DATETIME_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", username='" + getUsername() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", created_at='" + getCreatedAt() + "'" +
            "}";
    }

}
