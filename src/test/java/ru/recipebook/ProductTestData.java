package ru.recipebook;

import ru.recipebook.model.Product;

import java.util.List;

import static ru.recipebook.model.AbstractIdEntity.PRODUCT_SEQ;

public class ProductTestData {
    public static final int PRODUCT1_ID = PRODUCT_SEQ;

    public static final Product PRODUCT1 = new Product(PRODUCT1_ID,"Картошка",0.5,"кг");
    public static final Product PRODUCT2 = new Product(PRODUCT1_ID +1,"Яйцо",3.0,"шт");
    public static final Product PRODUCT3 = new Product(PRODUCT1_ID +2,"Морковь",0.2,"кг");
    public static final Product PRODUCT4 = new Product(PRODUCT1_ID +3,"Мясо",1.0,"кг");
    public static final Product PRODUCT5 = new Product(PRODUCT1_ID +4,"Картошка",0.6,"кг");
    public static final Product PRODUCT6 = new Product(PRODUCT1_ID +5,"Лук",0.2,"кг");
    public static final Product ADMIN_PRODUCT1 = new Product(PRODUCT1_ID +6,"Говядина",1.0,"кг");
    public static final Product ADMIN_PRODUCT2 = new Product(PRODUCT1_ID +7,"Макароны",300.0,"г");
    public static final Product ADMIN_PRODUCT3 = new Product(PRODUCT1_ID +8,"Томаты",0.3,"кг");

    public static final List<Product> USER_PRODUCTS = List.of(PRODUCT1, PRODUCT2, PRODUCT3, PRODUCT4, PRODUCT5, PRODUCT6);
    public static final List<Product> ADMIN_PRODUCTS = List.of(ADMIN_PRODUCT1, ADMIN_PRODUCT2, ADMIN_PRODUCT3);

    public static final Product NEW_PRODUCT1 = new Product(null,"Мясо",0.5,"кг");
    public static final Product NEW_PRODUCT2 = new Product(null,"Лук",300.0,"г");
    public static final Product NEW_PRODUCT3 = new Product(null,"Морковь",0.2,"кг");

    public static final List<Product> NEW_PRODUCTS = List.of(NEW_PRODUCT1,NEW_PRODUCT2,NEW_PRODUCT3);
}
