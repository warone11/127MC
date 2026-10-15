package com.warone11;

import java.sql.Connection;

import com.warone11.database.DatabaseManager;

public class Test {
    public static void main(String[] args) {
        Connection con = DatabaseManager.getConnection();
    }
}
