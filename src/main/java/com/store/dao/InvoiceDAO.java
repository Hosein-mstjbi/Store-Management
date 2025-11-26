package com.store.dao;

import com.store.db.DBConnection;

import java.sql.*;

/**
 * دسترسی به جدول invoices و invoice_items
 */
public class InvoiceDAO {

    public void initTables() throws SQLException {
        String table1 = "CREATE TABLE IF NOT EXISTS invoices (" +
                "id SERIAL PRIMARY KEY, " +
                "created_at TIMESTAMP NOT NULL DEFAULT now(), " +
                "total NUMERIC(12, 2) NOT NULL)";

        String table2 = "CREATE TABLE IF NOT EXISTS invoice_items (" +
                "id SERIAL PRIMARY KEY, " +
                "invoice_id INTEGER REFERENCES invoices(id) ON DELETE CASCADE, " +
                "qty INTEGER NOT NULL, " +
                "unit_price NUMERIC(12, 2) NOT NULL)";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(table1);
            statement.execute(table2);
        }
    }
}
