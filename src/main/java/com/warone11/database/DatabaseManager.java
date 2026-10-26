package com.warone11.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
/** Manages the connection to the database
 */
public class DatabaseManager {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection con;
    public static final DateTimeFormatter SQLITE_DATETIME_FORMAT =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Connection getConnection(){
        if(con != null){
            return con;
        }
        try {
            Files.createDirectories(Path.of("data"));
        } catch (Exception e) {
            
        }
        
        String url = "jdbc:sqlite:data/127mc.db";
        try{
            con= DriverManager.getConnection(url, USERNAME, PASSWORD);
            try (Statement statement = con.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON;");
            }
            Initialize();
             return con;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        
    }
    private static void Initialize(){
        String baseDatabase=null;
        try {
            baseDatabase = Files.readString(Path.of("src/main/resources/database/baseSchema.sql"));
            

        } catch (Exception e) {
            System.out.println("Path to sql not found");
        } 
        if(baseDatabase!=null){
            String[] stmts = baseDatabase.split(";");
            try(Statement stmt = con.createStatement()){
                for (String string : stmts) {
                    stmt.addBatch(string);
                }
                stmt.executeBatch();
                System.out.println("Database initialized sucessfully");
                
            }catch (SQLException e) {
                System.err.println("Error in database initialization "+e);
            }
        }
    }
}