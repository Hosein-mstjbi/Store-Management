package com.store.ui;

import com.store.service.StoreService;

import java.util.Scanner;

/**
 * مدیریت منو و تعامل با کاربر از طریق کنسول
 */
public class ConsoleUI {

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
