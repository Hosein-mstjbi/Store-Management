package com.store.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection : میریت Connection  به دیتابیس
 * URL : رشته اتصال به Postgresql
 * USER : نام کاربری دیتابیس
 * PASS : رمز عبور دیتابیس
 */
public class DBConnection {

    public static final String URL = "jdbc:postgresql://localhost:5433/storedb";
    public static final String USER = "postgres";
    public static final String PASS = "12345";

    static {
        //اطمینان از بارگذاری درایور پستگرس
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Add dependency to pom.xml");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
