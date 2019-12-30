package ru.recipebook;

import ru.recipebook.model.Product;
import ru.recipebook.model.Recipe;
import ru.recipebook.to.RecipeTo;

import java.nio.channels.ReadableByteChannel;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.recipebook.model.AbstractIdEntity.RECIPE_SEQ;
import static ru.recipebook.ProductTestData.*;

public class RecipeTestData {
    public static final int RECIPE1_ID = RECIPE_SEQ;
    public static final int ADMIN_RECIPE_ID = RECIPE_SEQ + 2;

    public static final Recipe RECIPE1 = new Recipe(RECIPE1_ID, of(2019, Month.MARCH, 29, 10, 0), "Завтрак", 4);
    public static final Recipe RECIPE2 = new Recipe(RECIPE1_ID +1, of(2019, Month.MARCH, 30, 13, 0), "Обед", 3);
    public static final Recipe ADMIN_RECIPE = new Recipe(ADMIN_RECIPE_ID, of(2019, Month.MARCH, 31, 21, 0), "Админ ужин", 4);
    static {
        RECIPE1.setProductList(List.of(PRODUCT1,PRODUCT2,PRODUCT3));
        RECIPE2.setProductList(List.of(PRODUCT4,PRODUCT5,PRODUCT6));
        ADMIN_RECIPE.setProductList(ADMIN_PRODUCTS);
    }

    public static final List<Recipe> RECIPES = List.of(RECIPE2, RECIPE1);

    public static Recipe getNew() {
        Recipe recipe = new Recipe(null, of(2019, Month.APRIL, 1, 18, 0), "Ужин", 3);
        recipe.setProductList(NEW_PRODUCTS);
        return recipe;
    }

    public static Recipe getUpdated() {
        return new Recipe(RECIPE1_ID, RECIPE1.getDateTime(), "Обновленный завтрак", 5);
    }

    public static TestMatchers<Recipe> RECIPE_MATCHERS = TestMatchers.useFieldsComparator(Recipe.class, "user","productList");
    public static TestMatchers<RecipeTo> RECIPE_TO_MATCHERS = TestMatchers.useEquals(RecipeTo.class);
    public static TestMatchers<Product> PRODUCT_MATCHERS = TestMatchers.useEquals(Product.class);
}
