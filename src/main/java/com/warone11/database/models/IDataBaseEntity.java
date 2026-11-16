package com.warone11.database.models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface IDataBaseEntity {
    public IDataBaseEntity create(ResultSet rs) throws SQLException;
    public void getValues(PreparedStatement stmt) throws SQLException;
    public Map<String,Object> toMap();
    public String SQLEqualsQuery();
}