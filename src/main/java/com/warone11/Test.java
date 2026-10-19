package com.warone11;

import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.warone11.database.DatabaseManager;

public class Test {
    public static void main(String[] args) {
        
        Connection con = DatabaseManager.getConnection();
        String sql="SELECT * FROM role";
        try(Statement stmt = con.createStatement()){
            ResultSet rs =stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(meta.getColumnLabel(i) + "=" + rs.getObject(i) + " ");
                }
                System.out.println();
            }
        }catch (SQLException e) {
            // TODO: handle exception
        }
        
    }
}
