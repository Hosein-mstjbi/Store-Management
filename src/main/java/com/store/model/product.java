package com.store.model;

/**
 * id : شناسه داخلی
 * sku : کد یکتا برای محصول (مثلا بارکد)
 * name : نام محصول
 * price : قیمت واحد
 * quantity : موجودی انبار
 */
public class product {
    public Integer id;
    public String sku;
    public String name;
    public double price;
    public int quantity;

    public product() {
    }

    public product(Integer id, String sku, String name, double price, int quantity) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
