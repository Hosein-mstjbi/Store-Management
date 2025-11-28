package com.store.ui;

import com.store.model.Product;
import com.store.service.StoreService;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * مدیریت منو و تعامل با کاربر از طریق کنسول
 */
public class ConsoleUI {
    private final Scanner input = new Scanner(System.in);
    private final StoreService service = new StoreService();

    public void start() {
        try {
            service.init();
        } catch (SQLException e) {
            print("خطا در اتصال یا مقدار دهی اولیه دیتابی : " + e.getMessage());
        }

        while (true) {
            showMenu();
            String choice = input.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        handleAddProduct();
                        break;
                    case "2":
                        handlePurchase();
                        break;
                    case "3":
                        handleSell();
                    case "4":
                        handleListProducts();
                        break;
                    case "5":
                        handleInventory();
                        break;
                    case "0":
                        print("خدانگهـــــــــدار!");
                        return;
                    default:
                        print("گزینه نامعتبر. دوباره تلاش کنید.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("ورودی عددی نامعتبر است.");
            } catch (SQLException e) {
                print("خطای دیتابیس : " + e.getMessage());
            }
        }
    }

    private void handleInventory() {

    }

    private void handleListProducts() {

    }

    private void handleSell() {
    }

    private void handlePurchase() {
    }

    private void handleAddProduct() throws SQLException {
        print("SKU: ");
        String sku = input.nextLine().trim();
        print("Name: ");
        String name = input.nextLine().trim();
        print("Price: ");
        double price = Double.parseDouble(input.nextLine().trim());
        print("Initial quantity: ");
        int qty = Integer.parseInt(input.nextLine().trim());
        Product product = service.addProduct(sku, name, price, qty);
        print("محصول ثبت شده : " + product);
    }

    private void showMenu() {
        print("\n === سیستم مدیریت فروشگاه مواد غذایی ===");
        print("1. ثبت کالای جدید");
        print("2. خرید (افزایش موجودی)");
        print("3. فروش / صدور فاکتور");
        print("4. نمایش همه کالا ها");
        print("5. مشاهده موجودی انبار (کالاها با تعداد)");
        print("0. خروج");
    }

    private void print(String message) {
        System.out.println(message);
    }
}
