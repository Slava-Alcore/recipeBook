package ru.recipebook.service.datajpa;

import org.hibernate.collection.internal.PersistentBag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.recipebook.ProductTestData;
import ru.recipebook.RecipeTestData;
import ru.recipebook.UserTestData;
import ru.recipebook.model.Product;
import ru.recipebook.model.Recipe;
import ru.recipebook.service.AbstractServiceTest;
import ru.recipebook.service.RecipeService;
import ru.recipebook.util.exception.ErrorType;
import ru.recipebook.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ru.recipebook.Profiles.*;
import static ru.recipebook.RecipeTestData.*;
import static ru.recipebook.UserTestData.*;
import static ru.recipebook.ProductTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaRecipeServiceTest extends AbstractServiceTest {
    @Test
    void getWithProduct() throws Exception {
        Recipe adminRecipe = service.getWithProducts(ADMIN_RECIPE_ID, ADMIN_ID);
        RECIPE_MATCHERS.assertMatch(adminRecipe, ADMIN_RECIPE);
        List<Product> products = new ArrayList<>(adminRecipe.getProductList());
        PRODUCT_MATCHERS.assertMatch(products, ADMIN_PRODUCTS);
    }

    @Test
    void getWithProductNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.getWithProducts(1, ADMIN_ID));
    }

    @Autowired
    protected RecipeService service;

    @Test
    void delete() throws Exception {
        service.delete(RecipeTestData.RECIPE1_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(RecipeTestData.RECIPE1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, USER_ID));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(RecipeTestData.RECIPE1_ID, ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Recipe newRecipe = RecipeTestData.getNew();
        Recipe created = service.create(newRecipe, USER_ID);
        Integer newId = created.getId();
        newRecipe.setId(newId);
        RECIPE_MATCHERS.assertMatch(created, newRecipe);
        RECIPE_MATCHERS.assertMatch(service.get(newId, USER_ID), newRecipe);
        List<Product> products = new ArrayList<>(created.getProductList());
        PRODUCT_MATCHERS.assertMatch(products, newRecipe.getProductList());
    }

    @Test
    void get() throws Exception {
        Recipe actual = service.get(ADMIN_RECIPE_ID, ADMIN_ID);
        RECIPE_MATCHERS.assertMatch(actual, ADMIN_RECIPE);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1, ADMIN_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(RecipeTestData.RECIPE1_ID, ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        Recipe updated = RecipeTestData.getUpdated();
        service.update(updated, USER_ID);
        RECIPE_MATCHERS.assertMatch(service.get(RecipeTestData.RECIPE1_ID, USER_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(RECIPE1, ADMIN_ID));
        String msg = e.getMessage();
        assertTrue(msg.contains(ErrorType.DATA_NOT_FOUND.name()));
        assertTrue(msg.contains(NotFoundException.NOT_FOUND_EXCEPTION));
        assertTrue(msg.contains(String.valueOf(RecipeTestData.RECIPE1_ID)));
    }

    @Test
    void getAll() throws Exception {
        RECIPE_MATCHERS.assertMatch(service.getAll(USER_ID), RECIPES);
    }

    @Test
    void getBetween() throws Exception {
        RECIPE_MATCHERS.assertMatch(service.getBetweenDates(
                new GregorianCalendar(2019, Calendar.MARCH, 30).getTime(),
                new GregorianCalendar(2019, Calendar.MARCH, 30).getTime(), USER_ID), RECIPE2);
    }

    @Test
    void getBetweenWithNullDates() throws Exception {
        RECIPE_MATCHERS.assertMatch(service.getBetweenDates(null, null, USER_ID), RECIPES);
    }

    @Test
    void createWithException() throws Exception {
        Recipe r1 = new Recipe(null, "  ", 4, USER_PRODUCTS);
        r1.setDate(new Date(2019, Calendar.APRIL, 1));
        validateRootCause(() -> service.create(r1, USER_ID), ConstraintViolationException.class);
        Recipe r2 = new Recipe(null, "Description", 5, USER_PRODUCTS);
        validateRootCause(() -> service.create(r2, USER_ID), ConstraintViolationException.class);
        Recipe r3 = new Recipe(null,"Description", 300, USER_PRODUCTS);
        r3.setDate(new Date(2019, Calendar.APRIL, 1));
        validateRootCause(() -> service.create(r3, USER_ID), ConstraintViolationException.class);
        Recipe r4 = new Recipe(null, "Description", 10, null);
        r4.setDate(new Date(2019, Calendar.APRIL, 1));
        validateRootCause(() -> service.create(r4, USER_ID), ConstraintViolationException.class);
    }
}
