package ru.recipebook;

import ru.recipebook.model.Product;
import ru.recipebook.model.Recipe;
import ru.recipebook.to.RecipeTo;

import java.nio.channels.ReadableByteChannel;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.recipebook.model.AbstractIdEntity.RECIPE_SEQ;
import static ru.recipebook.ProductTestData.*;

public class RecipeTestData {
    public static final int RECIPE1_ID = RECIPE_SEQ;
    public static final int ADMIN_RECIPE_ID = RECIPE_SEQ + 2;

    public static final Recipe RECIPE1 = new Recipe(RECIPE1_ID, "Завтрак", 4,List.of(PRODUCT1,PRODUCT2,PRODUCT3));
    public static final Recipe RECIPE2 = new Recipe(RECIPE1_ID +1, "Обед", 3,List.of(PRODUCT4,PRODUCT5,PRODUCT6));
    public static final Recipe ADMIN_RECIPE = new Recipe(ADMIN_RECIPE_ID, "Админ ужин", 4,ADMIN_PRODUCTS);
    static {
        RECIPE1.setDate(new GregorianCalendar(2019, Calendar.MARCH, 29).getTime());
        RECIPE2.setDate(new GregorianCalendar(2019, Calendar.MARCH, 30).getTime());
        ADMIN_RECIPE.setDate(new GregorianCalendar(2019, Calendar.MARCH, 31).getTime());
    }

    public static final List<Recipe> RECIPES = List.of(RECIPE2, RECIPE1);

    public static Recipe getNew() {
        Recipe newRecipe = new Recipe(null,  "Большой Ужин", 3,NEW_PRODUCTS);
        for (Product p : newRecipe.getProductList()){
            p.setRecipe(newRecipe);
        }
        return newRecipe;
    }

    public static Recipe getUpdated() {
        PRODUCT1.setRecipe(RECIPE1);
        PRODUCT2.setRecipe(RECIPE1);
        PRODUCT3.setRecipe(RECIPE1);
        Recipe updated = new Recipe(RECIPE1_ID,"Обновленный завтрак", 5,List.of(PRODUCT1));
        updated.setDate(RECIPE1.getDate());
        return updated;
    }

    public static TestMatchers<Recipe> RECIPE_MATCHERS = TestMatchers.useFieldsComparator(Recipe.class, "user","productList","date");
    public static TestMatchers<RecipeTo> RECIPE_TO_MATCHERS = TestMatchers.useEquals(RecipeTo.class);
    public static TestMatchers<Product> PRODUCT_MATCHERS = TestMatchers.useFieldsComparator(Product.class,"recipe");
}
