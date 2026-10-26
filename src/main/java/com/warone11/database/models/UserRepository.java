package com.warone11.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.warone11.database.DatabaseManager;

public class UserRepository {

    private Connection con;
    public UserRepository(){
        con=DatabaseManager.getConnection();
    }
    public List<User> findAll(){
        List<User> users= new ArrayList<>();
        String query="SELECT * FROM user";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()){
                users.add(new User(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL exception detected trying to obtain all users:\n"+e);
            return null;
        }
        return users;
    }
    public boolean insert(User user){
        String query="INSERT INTO user values(?,?,?,?)";
        try (PreparedStatement stmt= con.prepareStatement(query)) {
            stmt.setString(1, user.getUuid().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getCreatedAtAsSQL());
            return stmt.executeUpdate()!=0;
        } catch (SQLException e) {
            System.err.println("SQL exception detected trying to insert user:\n"+e);
        }
        return false;
    }
    public boolean exists(UUID uuid){
        String query="SELECT uuid FROM user where uuid='"+uuid.toString()+"'";
        try (Statement stmt = con.createStatement()) {
            return stmt.executeQuery(query).next();            
        } catch (Exception e) {
            System.err.println("SQL exception detected trying to select from user:\n"+e);
        }
        return false;
    }
    public boolean remove(UUID uuid){
        
    }
}
