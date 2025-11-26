package com.store.dao;

import com.store.db.DBConnection;
import com.store.model.Product;

import java.sql.*;

/**
 * درسترسی به جدول products
 */
public class ProductDAO {

    /**
     * ایجاد جداول مورد نیاز در صورتی که وجود نداشته باشند
     */
    public void initTable() throws SQLException {
        String query = "CRATE TABLE IF NOT EXISTS products (" +
                "id SERIAL PRIMARY KEY," +
                "sku VARCHAR(50) UNIQUE NOT NULL," +
                "name VARCHAR(200) NOT NULL," +
                "price NUMERIC(12,2) NOT NULL," +
                "quantity INTEGER NOT NULL DEFAULT 0" +
                ")";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }
}
