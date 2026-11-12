package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import com.warone11.database.DatabaseManager;
import com.warone11.database.utils.SQLUtils;

public class Role implements IDataBaseEntity{

    
    public enum Permission{
        can_start,
        can_control_state,
        can_modify,
        can_access_files,
        can_see_console,
        can_use_console,
        can_see_history,
        is_administrator
    }
    private UUID uuid;
    private String name;
    private boolean systemRole;
    private LocalDateTime createdAt;
    private EnumSet<Permission> permissions;
    public static final String[] COLUMN_ARRAY = {"uuid","name","systemRole","created_at","can_start","can_control_state","can_modify","can_access_files","can_see_console","can_use_console","can_see_history","is_administrator"};
    public static final String TABLE_NAME = "role";
    public static final Role PREFAB= new Role(UUID.randomUUID(),"default",false,null,EnumSet.noneOf(Permission.class));
    public Role(UUID uuid, String name, boolean systemRole, LocalDateTime createdAt, Set<Permission> permissions) {
        if(uuid==null) throw new IllegalArgumentException("field uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("UUID = "+uuid+": Field name cannot be null");
        if(permissions==null) throw new IllegalArgumentException("UUID = "+uuid+": Field permissions cannot be null");
        if(createdAt==null) createdAt=LocalDateTime.now();
        this.uuid = uuid;
        this.name = name;
        this.systemRole = systemRole;
        this.createdAt = createdAt;
        this.permissions = EnumSet.copyOf(permissions);
    }
    public Role(ResultSet rs) throws SQLException{
        this(
            UUID.fromString(rs.getString("uuid")),
            rs.getString("name"),
            rs.getBoolean("systemRole"),
            SQLUtils.DateTimeSQLToJava(rs.getString("created_at")),
            EnumSet.noneOf(Permission.class)
        );
        for(Permission p:Permission.values()){
            if(rs.getBoolean(p.name())){
                this.permissions.add(p);
            }
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSystemRole() {
        return this.systemRole;
    }

    public boolean getSystemRole() {
        return this.systemRole;
    }

    public void setSystemRole(boolean systemRole) {
        this.systemRole = systemRole;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean hasPermission(Permission p){
        return this.permissions.contains(p);
    }
    public void addPermission(Permission p){
        this.permissions.add(p);
    }
    public void removePermission(Permission p){
        this.permissions.remove(p);
    }

    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new Role(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, this.uuid.toString());
        stmt.setString(2, this.name);
        stmt.setBoolean(3, this.systemRole);
        stmt.setString(4, SQLUtils.DateTimeJavaToSQL(createdAt));
        int index =5;
        for(Permission p:Permission.values()){
            stmt.setBoolean(index, this.permissions.contains(p));
            index++;
        }

    }
}
