package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.DatabaseManager;
import com.warone11.database.utils.SQLUtils;

public class AccessToken implements IDataBaseEntity {
    // ************* ATRIBUTES *************
    private UUID uuid;
    private String name;
    private String hash;
    private UUID userUUID;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;

    // ************* CONSTRUCTORS *************
    /**
     * Constructor of the AccessToken class
     * @param uuid unique identifier of the token
     * @param name name of the token
     * @param hash hash of the token
     * @param userUUID uuid of the user asigned to the token
     * @param createdAt time of creation of the token
     * @param expiresAt time of expiration of the token, if null the token will never expire
     * @param active if the token is active or not
     * 
     * @throws IllegalArgumentException if uuid, name, hash or userUuid is null
     */
    public AccessToken(UUID uuid, String name, String hash, UUID userUUID, LocalDateTime createdAt, LocalDateTime expiresAt, boolean active) {
        if(uuid==null) throw new IllegalArgumentException("uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("name cannot be null");
        if(hash==null) throw new IllegalArgumentException("hash cannot be null");
        if(userUUID==null) throw new IllegalArgumentException("userUUID cannot be null");
        if(createdAt==null) createdAt= LocalDateTime.now();
        this.uuid = uuid;
        this.name = name;
        this.hash = hash;
        this.userUUID = userUUID;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.active = active;
    }
    public AccessToken(ResultSet rs) throws SQLException{
        this(
            UUID.fromString(rs.getString("uuid")),
            rs.getString("name"),
            rs.getString("hash"),
            UUID.fromString(rs.getString("user_uuid")),
            SQLUtils.DateTimeSQLToJava(rs.getString("created_at")),
            SQLUtils.DateTimeSQLToJava(rs.getString("expires_at")),
            rs.getBoolean("active")
        );
    }
    // ************* GETTERS AND SETTERS *************

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        if(uuid==null) throw new IllegalArgumentException("uuid cannot be null");
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name==null) throw new IllegalArgumentException("name cannot be null");
        this.name = name;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        if(hash==null) throw new IllegalArgumentException("hash cannot be null");
        this.hash = hash;
    }

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        if(userUUID==null) throw new IllegalArgumentException("userUUID cannot be null");
        this.userUUID = userUUID;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new AccessToken(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
