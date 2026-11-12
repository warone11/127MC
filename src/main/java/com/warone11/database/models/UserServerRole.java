package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserServerRole implements IDataBaseEntity {
    private UUID userUUID;
    private UUID serverUUID;
    private UUID roleUUID;

    public static final String TABLE_NAME="user_server_role";
    public static final String[] COLUMN_ARRAY={"user_uuid","server_uuid","action_uuid"};
    public static final UserServerRole PREFAB=new UserServerRole(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

    public UserServerRole(UUID userUUID, UUID serverUUID, UUID roleUUID) {
        if(userUUID==null) throw new IllegalArgumentException("userUUID cannot be null");
        if(serverUUID==null) throw new IllegalArgumentException("serverUUID cannot be null");
        if(roleUUID==null) throw new IllegalArgumentException("roleUUID cannot be null");
        this.userUUID = userUUID;
        this.serverUUID = serverUUID;
        this.roleUUID = roleUUID;
    }
    public UserServerRole(ResultSet rs) throws SQLException{
        userUUID=UUID.fromString(rs.getString("user_uuid"));
        serverUUID=UUID.fromString(rs.getString("server_uuid"));
        roleUUID=UUID.fromString(rs.getString("role_uuid"));
    }

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public UUID getServerUUID() {
        return this.serverUUID;
    }

    public void setServerUUID(UUID serverUUID) {
        this.serverUUID = serverUUID;
    }

    public UUID getRoleUUID() {
        return this.roleUUID;
    }

    public void setRoleUUID(UUID roleUUID) {
        this.roleUUID = roleUUID;
    }




    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new UserServerRole(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, userUUID.toString());
        stmt.setString(2, serverUUID.toString());
        stmt.setString(3, roleUUID.toString());
    }
    
}
