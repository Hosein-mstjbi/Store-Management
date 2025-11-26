package com.store.dao;

import com.store.db.DBConnection;
import com.store.model.Product;

import java.sql.*;
import java.util.Optional;

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

    /**
     * درج محصول جدید
     */
    public Product insert(Product product) throws SQLException {
        String query = "INSERT INTO products(sku, name, price, quantity) VALUES (?,?,?,?) RETURNING id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.sku);
            preparedStatement.setString(2, product.name);
            preparedStatement.setDouble(3, product.price);
            preparedStatement.setInt(4, product.quantity);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product.id = resultSet.getInt(1);
            }
            return product;
        }
    }

    /**
     * یافتن محصول بر اساس sku
     */
    public Optional<Product> findBySku(String sku) throws SQLException {
        String query = "SELECT id, sku, name, price, quantity FROM products WHERE sku = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sku);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapProduct(resultSet));
            }
            return Optional.empty();
        }
    }

    /**
     * یافتن محصول بر اساس id
     */
    public Optional<Product> findById(int id) throws SQLException {
        String query = "SELECT id, sku, name, price, quantity FROM products WHERE id = ?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Optional.of(mapProduct(resultSet));
            }
            return Optional.empty();
        }
    }

    private Product mapProduct(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getInt("id"),
                resultSet.getString("sku"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getInt("quarter"));
    }
}
