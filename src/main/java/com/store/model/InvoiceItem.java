package com.store.model;

/**
 * productId : شناسه محصول
 * qty : تعداد فروحته شده
 * unitPrice : قیمت واحد هنگام صدور فاکتور (برای ثبت قیمت تاریخی)
 */
public class InvoiceItem {
    public Integer productId;
    public int qty;
    public double unitPrice;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer productId, int qty, double unitPrice) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
