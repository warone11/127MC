package com.warone11.database.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.warone11.database.DatabaseManager;

public class SQLUtils {
    public static final DateTimeFormatter SQLITE_DATETIME_FORMAT =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static LocalDateTime DateTimeSQLToJava(String dateTime){
        return dateTime==null?null:LocalDateTime.parse(dateTime, SQLITE_DATETIME_FORMAT);
    }
    public static String DateTimeJavaToSQL(LocalDateTime dateTime){
        return dateTime==null?null:dateTime.format(SQLITE_DATETIME_FORMAT);
    }
}
