package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.DatabaseManager;
import com.warone11.database.utils.SQLUtils;
/**
 * Defines a user of the application
 */
public class User implements IDataBaseEntity{
    private UUID uuid;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;
    public static final String[] COLUMN_ARRAY = {"uuid","username","password_hash","created_at"};
    public static final String TABLE_NAME = "user";
    public static final User PREFAB= new User(UUID.randomUUID(),"default","default",null);
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
        if(createdAt==null)
            createdAt=LocalDateTime.now();
        this.uuid = uuid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }
    public User(ResultSet rs) throws SQLException{
        this(UUID.fromString(rs.getString("uuid")),rs.getString("username"),rs.getString("password_hash"),SQLUtils.DateTimeSQLToJava(rs.getString("created_at")));
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

    @Override
    public String toString() {
        return "----------------------\n"+
        "uuid: "+uuid+"\n" +
        "username: " + username + "\n" +
        "passwordHash: " + passwordHash + "\n" +
        "createdAt: " + createdAt + "\n";
    }
    @Override 
    public boolean equals(Object o){
        if(o!=null&&o instanceof User u){
            return u.uuid.equals(uuid);
        }
        return false;
    }

    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new User(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, uuid.toString());
        stmt.setString(2, username);
        stmt.setString(3, passwordHash);
        stmt.setString(4, SQLUtils.DateTimeJavaToSQL(createdAt));
    }
}
