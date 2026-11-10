package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.DatabaseManager;

public class AccessToken implements IDataBaseEntity {
    // ************* ATRIBUTES *************
    private UUID uuid;
    private String name;
    private String hash;
    private UUID userUuid;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;

    // ************* CONSTRUCTORS *************
    /**
     * Constructor of the AccessToken class
     * @param uuid unique identifier of the token
     * @param name name of the token
     * @param hash hash of the token
     * @param userUuid uuid of the user asigned to the token
     * @param createdAt time of creation of the token
     * @param expiresAt time of expiration of the token, if null the token will never expire
     * @param active if the token is active or not
     * 
     * @throws IllegalArgumentException if uuid, name, hash or userUuid is null
     */
    public AccessToken(UUID uuid, String name, String hash, UUID userUuid, LocalDateTime createdAt, LocalDateTime expiresAt, boolean active) {
        if(uuid==null) throw new IllegalArgumentException("uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("name cannot be null");
        if(hash==null) throw new IllegalArgumentException("hash cannot be null");
        if(userUuid==null) throw new IllegalArgumentException("userUuid cannot be null");
        if(createdAt==null) createdAt= LocalDateTime.now();
        this.uuid = uuid;
        this.name = name;
        this.hash = hash;
        this.userUuid = userUuid;
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
            rs.getString("created_at")==null?null:LocalDateTime.parse(rs.getString("created_at"),DatabaseManager.SQLITE_DATETIME_FORMAT),
            rs.getString("expires_at")==null?null:LocalDateTime.parse(rs.getString("expires_at"), DatabaseManager.SQLITE_DATETIME_FORMAT),
            rs.getBoolean("active")
        );
    }
    // ************* GETTERS AND SETTERS *************

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
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

    public UUID getUserUuid() {
        return this.userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        if(userUuid==null) throw new IllegalArgumentException("userUuid cannot be null");
        this.userUuid = userUuid;
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
