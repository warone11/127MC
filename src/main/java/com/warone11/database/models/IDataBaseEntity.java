package com.warone11.database.models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataBaseEntity {
    public IDataBaseEntity create(ResultSet rs) throws SQLException;
    public void getValues(PreparedStatement stmt) throws SQLException;
}