package com.store.service;

import com.store.dao.InvoiceDAO;
import com.store.dao.ProductDAO;
import com.store.db.DBConnection;
import com.store.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * منطق برنامه
 */
public class StoreService {
    private final ProductDAO productDAO = new ProductDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    /**
     * مقدار دهی اولیه جداول
     */
    public void init() throws SQLException {
        productDAO.initTables();
        invoiceDAO.initTables();
    }

    /**
     * ثبت محصول حدید
     */
    public Product addProduct(String sku, String name, double price, int qty) throws SQLException {
        Product product = new Product(null, sku, name, price, qty);
        return productDAO.insert(product);
    }

    /**
     * افزایش موجودی (خرید برای انبار)- به صورت اتوماتیک مقدار جدید را در جدول ذخیره میکند
     */
    public void addInventory(String sku, int addQty) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            Optional<Product> optional = productDAO.findBySku(sku);
            if (optional.isPresent()) {
                throw new SQLException("Product not found");
            }
            Product p = optional.get();
            int newQty = p.quantity + addQty;
            productDAO.updateQuantity(p.id, newQty, connection);
        }
    }
}
