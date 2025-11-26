package com.store.dao;

import com.store.db.DBConnection;
import com.store.model.InvoiceItem;

import java.sql.*;
import java.util.List;

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
                "product_id INTEGER," +
                "qty INTEGER NOT NULL, " +
                "unit_price NUMERIC(12, 2) NOT NULL)";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(table1);
            statement.execute(table2);
        }
    }

    /**
     * ایجاد فاکتور به همراه آیتم ها در یک تراکنش
     * این متد فرض میکند که موجودی محصول قبل از فراخوانی بررسی شده و کافی است
     */
    public int createInvoice(List<InvoiceItem> items, Connection conn) throws SQLException {
        //محاسبه مجموع
        double total = 0.0;
        for (InvoiceItem item : items) {
            total += item.qty * item.unitPrice;
        }
        String insertInvoice = "INSERT INTO invoices (total) VALUES (?) RETURNING id";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertInvoice)) {
            preparedStatement.setDouble(1, total);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int invoiceId = resultSet.getInt(1);
                String insertItem = "INSERT INTO invoice_items (invoice_id, product_id, qty, unit_price) VALUES (?,?,?,?)";
                try (PreparedStatement prepared = conn.prepareStatement(insertItem)) {
                    for (InvoiceItem it : items) {
                        prepared.setInt(1, invoiceId);
                        prepared.setInt(2, it.productId);
                        prepared.setInt(3, it.qty);
                        prepared.setDouble(4, it.unitPrice);
                        prepared.addBatch();
                    }
                    prepared.executeBatch();
                }
                return invoiceId;
            } else {
                throw new SQLException("Failed to create invoice");
            }
        }
    }
}
