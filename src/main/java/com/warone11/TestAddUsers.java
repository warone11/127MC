package com.warone11;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;

import com.warone11.database.DatabaseManager;

public class TestAddUsers {
    public static void main(String[] args) {
        String test=null;
        try{
             test= Files.readString(Path.of("src/main/resources/database/testData.sql"));
        }catch (Exception e) {
            
        }
        if(test!=null){
        String[] stmts = test.split(";");
            try(Statement stmt = DatabaseManager.getConnection().createStatement()){
                for (String string : stmts) {
                    stmt.addBatch(string);
                }
                stmt.executeBatch();
                System.out.println("Database initialized sucessfully");
                
            }catch (SQLException e) {
                System.err.println("Error "+e);
            }
        }
    }
}
