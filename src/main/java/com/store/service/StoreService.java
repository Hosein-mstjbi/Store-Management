package com.store.service;

import com.store.dao.InvoiceDAO;
import com.store.dao.ProductDAO;
import com.store.model.Product;

import java.sql.SQLException;

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
}
