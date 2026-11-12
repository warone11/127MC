package com.warone11;

import java.sql.Connection;

import com.warone11.database.DatabaseManager;
import com.warone11.database.models.Repository;
import com.warone11.database.models.User;

public class Test {
    public static void main(String[] args) {
        
        Connection con = DatabaseManager.getConnection();
        
        final Repository<User> userRepo = new Repository<>(User.PREFAB,con,User.TABLE_NAME,User.COLUMN_ARRAY);
        
        
    }
}
