package com.store.service;

import com.store.dao.InvoiceDAO;
import com.store.dao.ProductDAO;
import com.store.db.DBConnection;
import com.store.model.InvoiceItem;
import com.store.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * منطق برنامه
 */
public class StoreService {
    public final ProductDAO productDAO = new ProductDAO();
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
            if (optional.isEmpty()) {
                throw new SQLException("Product not found");
            }
            Product p = optional.get();
            int newQty = p.quantity + addQty;
            productDAO.updateQuantity(p.id, newQty, connection);
        }
    }

    /**
     * ایجاد فاکتور فروش : در یک تراکنش بررسی می کند که موجودی کافی هست، سپس فاکتور را میسازد و موجودی را کم میکند
     * خروجی : id فاکتور ایجاد شده
     */
    public int sell(List<InvoiceItem> items) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            try {
                connection.setAutoCommit(false);
                //بررسی موجودی
                for (InvoiceItem item : items) {
                    Optional<Product> optional = productDAO.findById(item.productId);
                    if (optional.isEmpty()) {
                        throw new SQLException("Product id " + item.productId + " not found");
                    }
                    if (optional.get().quantity < item.qty) {
                        throw new SQLException("Insufficient stock for product id " + item.productId);
                    }
                }
                //اعمال کاهش موجودی
                for (InvoiceItem item : items) {
                    Product product = productDAO.findById(item.productId).get();
                    int newQty = product.quantity - item.qty;
                    productDAO.updateQuantity(product.id, newQty, connection);
                }
                //درج فاکتور
                int invoiceId = invoiceDAO.createInvoice(items, connection);
                connection.commit();
                return invoiceId;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<Product> listProduct() throws SQLException {
        return productDAO.listAll();
    }

    public Optional<Product> findBySku(String sku) throws SQLException {
        return productDAO.findBySku(sku);
    }
}
