package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.utils.SQLConstants;
import com.warone11.database.utils.SQLUtils;

public class Server implements IDataBaseEntity{

    
    public enum ServerType{
        vanilla,
        forge,
        neoForge,
        fabric,
        quilt,
        paper
    }
    private UUID uuid;
    private String name;
    private ServerType type;
    private LocalDateTime created_at;
    private boolean locked;
    public static final String[] COLUMN_ARRAY={"uuid","name","type","created_at","locked"};
    public static final String TABLE_NAME="server";
    public static final Server PREFAB= new Server(UUID.randomUUID(),"default",ServerType.vanilla,null,false);
    

    public Server(UUID uuid, String name, ServerType type, LocalDateTime created_at, boolean locked) {
        if(uuid==null) throw new IllegalArgumentException("field uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("field name cannot be null");
        if(type==null) throw new IllegalArgumentException("field type cannot be null");
        if(created_at==null) created_at=LocalDateTime.now();
        if(uuid.equals(UUID.fromString(SQLConstants.GLOBAL_SERVER_UUID))) throw new IllegalArgumentException("Tried instancing system server");
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.created_at = created_at;
        this.locked = locked;
    }
    public Server(ResultSet rs) throws SQLException{
        this.uuid=UUID.fromString(rs.getString("uuid"));
        this.name=rs.getString("name");
        this.type=ServerType.valueOf(rs.getString("type"));
        this.created_at=SQLUtils.DateTimeSQLToJava(rs.getString("created_at"));
        this.locked=rs.getBoolean("locked");
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

    public ServerType getType() {
        return this.type;
    }

    public void setType(ServerType type) {
        if(type==null) throw new IllegalArgumentException("field type cannot be null");
        this.type = type;
    }

    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        if(created_at==null) created_at=LocalDateTime.now();
        this.created_at = created_at;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean getLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        if(rs.getString("uuid").equals(SQLConstants.GLOBAL_SERVER_UUID)) return null;
        return new Server(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, uuid.toString());
        stmt.setString(2, name);
        stmt.setString(3, type.name());
        stmt.setString(4, SQLUtils.DateTimeJavaToSQL(created_at));
        stmt.setBoolean(5, locked);
    }

    @Override
    public String toString() {
        return "-----------------------------\n"+
        "UUID: " + uuid+"\n"+
        "Name: " + name+"\n"+
        "Type: " + type+"\n"+
        "Created At: " + created_at+"\n"+
        "Locked: " + locked+"\n";
    }
    @Override 
    public boolean equals(Object o){
        if(o!=null&&o instanceof Server s){
            return s.getUuid().equals(uuid);
        }
        return false;
    }
    
    
}
