package com.warone11;

import java.sql.Statement;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.warone11.database.DatabaseManager;
import com.warone11.database.models.User;
import com.warone11.database.models.UserRepository;
import com.warone11.database.utils.ModifierBuilder;
import com.warone11.database.utils.SQLModifier.Operator;
import com.warone11.database.utils.SQLModifiers.ComparingColumns;
import com.warone11.database.utils.SQLModifiers.ComparingColumns.Comparator;
import com.warone11.database.utils.SQLModifiers.Order;
import com.warone11.database.utils.SQLModifiers.Pager;

public class Test {
    public static void main(String[] args) {
        
        Connection con = DatabaseManager.getConnection();
        UserRepository userRepo = new UserRepository();
        List<User> users;
        //users=userRepo.findAll();
        //users = userRepo.findAll(ComparingColumns.of("username", ComparingColumns.Comparator.LIKE, "%son%"));
        /*users = userRepo.findAll(
            ModifierBuilder.allAND(
                ComparingColumns.of("password_hash",Comparator.LIKE,"Z%"),
                ComparingColumns.of("password_hash",Comparator.LIKE,"%G%"),
                Order.by("username",Order.OrderType.DESC)
            )
        );*/
        users = userRepo.findAll(
            ModifierBuilder.custom(
                Pager.of(2, 4),
                ComparingColumns.of("uuid", ComparingColumns.Comparator.LIKE, "0%"),
                Operator.OR(ComparingColumns.of("username",ComparingColumns.Comparator.LIKE,"%son%")),
                Operator.AND(ComparingColumns.of("password_hash",Comparator.LIKE,"%0%"))
            )
        );
        System.out.println(users);
        /*System.out.println(ModifierBuilder.custom(
                Pager.of(2, 4),
                ComparingColumns.of("uuid", ComparingColumns.Comparator.LIKE, "0%"),
                Operator.OR(ComparingColumns.of("username",ComparingColumns.Comparator.LIKE,"%son%")),
                Operator.AND(ComparingColumns.of("password_hash",Comparator.LIKE,"%0%"))
            ).query);*/
        
    }
}
