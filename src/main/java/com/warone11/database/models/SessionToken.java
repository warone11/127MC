package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.utils.SQLUtils;

public class SessionToken implements IDataBaseEntity{
    private UUID uuid;
    private String name;
    private String hash;
    private LocalDateTime createdAt;
    private LocalDateTime lastUsed;
    private LocalDateTime expiresAt;
    private UUID userUUID;

    public static final String TABLE_NAME="session_token";
    public static final String[] COLUMN_ARRAY={"uuid","name","hash","created_at","last_used","expires_at","user_uuid"};
    public static final SessionToken PREFAB=new SessionToken(UUID.randomUUID(),"default","default",null,null,null,UUID.randomUUID());

    public SessionToken(UUID uuid, String name, String hash, LocalDateTime createdAt, LocalDateTime lastUsed, LocalDateTime expiresAt, UUID userUUID) {
        if(uuid==null) throw new IllegalArgumentException("field uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("field name cannot be null");
        if(hash==null) throw new IllegalArgumentException("field hash cannot be null");
        if(userUUID==null) throw new IllegalArgumentException("field userUUID cannot be null");
        if(createdAt==null) createdAt=LocalDateTime.now();
        if(lastUsed==null) lastUsed=LocalDateTime.now();
        this.uuid = uuid;
        this.name = name;
        this.hash = hash;
        this.createdAt = createdAt;
        this.lastUsed = lastUsed;
        this.expiresAt = expiresAt;
        this.userUUID=userUUID;
    }
    public SessionToken(ResultSet rs) throws SQLException{
        uuid=UUID.fromString(rs.getString("uuid"));
        name=rs.getString("name");
        hash=rs.getString("hash");
        createdAt=SQLUtils.DateTimeSQLToJava(rs.getString("created_at"));
        lastUsed=SQLUtils.DateTimeSQLToJava(rs.getString("last_used"));
        expiresAt=SQLUtils.DateTimeSQLToJava(rs.getString("expires_at"));
        userUUID=UUID.fromString(rs.getString("user_uuid"));
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        if(uuid==null) throw new IllegalArgumentException("field uuid cannot be null");
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name==null) throw new IllegalArgumentException("field name cannot be null");
        this.name = name;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        if(hash==null) throw new IllegalArgumentException("field hash cannot be null");
        this.hash = hash;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if(createdAt==null) createdAt=LocalDateTime.now();
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUsed() {
        return this.lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        if(lastUsed==null) lastUsed=LocalDateTime.now();
        this.lastUsed = lastUsed;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    public void setUserUUID(UUID userUUID) {
        if(userUUID==null) throw new IllegalArgumentException("field userUUID cannot be null");
        this.userUUID = userUUID;
    }
    public UUID getUserUUID() {
        return userUUID;
    }
    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new SessionToken(rs);
    }
    

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1,uuid.toString());
        stmt.setString(2,name);
        stmt.setString(3,hash);
        stmt.setString(4,SQLUtils.DateTimeJavaToSQL(createdAt));
        stmt.setString(5,SQLUtils.DateTimeJavaToSQL(lastUsed));
        stmt.setString(6,SQLUtils.DateTimeJavaToSQL(expiresAt));
        stmt.setString(7,userUUID.toString());
    }

    @Override
    public String toString() {
        return "---------------------\n"+
                "uuid: "+uuid+"\n"+
                "name: "+name+"\n"+
                "hash: "+hash+"\n"+
                "createdAt: "+createdAt+"\n"+
                "lastUsed: "+lastUsed+"\n"+
                "expiresAt: "+expiresAt+"\n"+
                "userUUID: "+userUUID+"\n";
    }
    @Override 
    public boolean equals(Object o){
        if(o!=null&&o instanceof SessionToken st){
            return st.getUuid().equals(uuid);
        }
        return false;
    }
    
    
    
    
}
