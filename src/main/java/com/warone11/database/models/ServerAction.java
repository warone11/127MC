package com.warone11.database.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.warone11.database.utils.SQLUtils;

public class ServerAction implements IDataBaseEntity {
    public enum ActionType{
        create,
        delete,
        modify_server,
        change_state,
        modify_permission,
        used_console
    }
    private UUID uuid;
    private String name;
    private LocalDateTime actionTime;
    private UUID userUUID;
    private UUID serverUUID;
    private ActionType actionType;
    public static final String TABLE_NAME="server_action";
    public static final String[] COLUMN_ARRAY={"uuid","name","action_time","user_uuid","server_uuid","action_type"};
    public static final ServerAction PREFAB=new ServerAction(UUID.randomUUID(),"default",null,UUID.randomUUID(),UUID.randomUUID(),ActionType.create);

    public ServerAction(UUID uuid, String name, LocalDateTime actionTime, UUID userUUID, UUID serverUUID, ActionType actionType) {
        if(uuid==null) throw new IllegalArgumentException("field uuid cannot be null");
        if(name==null) throw new IllegalArgumentException("field name cannot be null");
        if(userUUID==null) throw new IllegalArgumentException("field userUUID cannot be null");
        if(serverUUID==null) throw new IllegalArgumentException("field serverUUID cannot be null");
        if(actionType==null) throw new IllegalArgumentException("field actionType cannot be null");
        if(actionTime==null) actionTime=LocalDateTime.now();
        this.uuid = uuid;
        this.name = name;
        this.actionTime = actionTime;
        this.userUUID=userUUID;
        this.serverUUID=serverUUID;
        this.actionType=actionType;
    }
    public ServerAction(ResultSet rs) throws SQLException{
        uuid=UUID.fromString(rs.getString("uuid"));
        name=rs.getString("name");
        actionTime=SQLUtils.DateTimeSQLToJava(rs.getString("action_time"));
        userUUID=UUID.fromString(rs.getString("user_uuid"));
        serverUUID=UUID.fromString(rs.getString("server_uuid"));
        actionType=ActionType.valueOf(rs.getString("action_type"));
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

    public LocalDateTime getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        if(actionTime==null) actionTime=LocalDateTime.now();
        this.actionTime = actionTime;
    }

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        if(userUUID==null) throw new IllegalArgumentException("field userUUID cannot be null");
        this.userUUID = userUUID;
    }

    public UUID getServerUUID() {
        return this.serverUUID;
    }

    public void setServerUUID(UUID serverUUID) {
        if(serverUUID==null) throw new IllegalArgumentException("field serverUUID cannot be null");
        this.serverUUID = serverUUID;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        if(actionType==null) throw new IllegalArgumentException("field actionType cannot be null");
        this.actionType = actionType;
    }


    @Override
    public IDataBaseEntity create(ResultSet rs) throws SQLException {
        return new ServerAction(rs);
    }

    @Override
    public void getValues(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, uuid.toString());
        stmt.setString(2, name);
        stmt.setString(3, SQLUtils.DateTimeJavaToSQL(actionTime));
        stmt.setString(4, userUUID.toString());
        stmt.setString(5, serverUUID.toString());
        stmt.setString(6, actionType.toString());
    }

    @Override
    public String toString() {
        return "-----------------------\n"+
                "uuid: "+uuid+"\n"+
                "name: "+name+"\n"+
                "actionTime: "+actionTime+"\n"+
                "userUUID: "+userUUID+"\n"+
                "serverUUID: "+serverUUID+"\n"+
                "actionType: "+actionType+"\n";

    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof ServerAction sa){
            return uuid.equals(sa.getUuid());
        }
        return false;
    }
    
    
    
}
