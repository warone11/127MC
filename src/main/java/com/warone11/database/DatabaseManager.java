package com.warone11.database;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/** Manages the connection to the database
 */
public class DatabaseManager {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection con;


    public static Connection getConnection(){

        String url = "jdbc:sqlite:data/127mc.db";
        boolean dataBaseExists =Files.exists(Path.of("data/127mc.db"));
        try{
             Connection con= DriverManager.getConnection(url, USERNAME, PASSWORD);
             try (Statement statement = con.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON;");
             } 
             if(!dataBaseExists){
                Initialize(con);
             }
             return con;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        
    }
    private static void Initialize(Connection con){
        
    }
}