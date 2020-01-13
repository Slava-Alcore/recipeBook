package ru.recipebook.web.recipe;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.recipebook.ProductTestData;
import ru.recipebook.RecipeTestData;
import ru.recipebook.model.Product;
import ru.recipebook.model.Recipe;
import ru.recipebook.service.RecipeService;
import ru.recipebook.util.exception.NotFoundException;
import ru.recipebook.web.AbstractControllerTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.recipebook.RecipeTestData.*;
import static ru.recipebook.TestUtil.readFromJson;
import static ru.recipebook.TestUtil.readFromJsonMvcResult;
import static ru.recipebook.UserTestData.*;
import static ru.recipebook.model.AbstractIdEntity.RECIPE_SEQ;
import static ru.recipebook.util.RecipeUtil.createTo;
import static ru.recipebook.util.RecipeUtil.getTos;
import static ru.recipebook.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.recipebook.ProductTestData.*;

class RecipeRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RecipeService recipeService;

    RecipeRestControllerTest() {
        super(RecipeRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(ADMIN_RECIPE_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> RECIPE_MATCHERS.assertMatch(readFromJsonMvcResult(result, Recipe.class), ADMIN_RECIPE));
    }


    @Test
    void getUnauth() throws Exception {
        perform(doGet(RECIPE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(ADMIN_RECIPE_ID).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(RECIPE1_ID).basicAuth(USER))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> recipeService.delete(RECIPE1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(ADMIN_RECIPE_ID).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Recipe updated = RecipeTestData.getUpdated();
        updated.setProductList(List.of(PRODUCT1,PRODUCT2,PRODUCT3));
        perform(doPut(RECIPE1_ID).jsonBody(updated).basicAuth(USER))
                .andExpect(status().isNoContent());

        RECIPE_MATCHERS.assertMatch(recipeService.get(RECIPE1_ID, USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RECIPE_TO_MATCHERS.contentJson(getTos(RECIPES)));
    }

    @Test
    void filter() throws Exception {
        perform(doGet("filter").basicAuth(USER).unwrap()
                .param("startDate", "2019-03-30")
                .param("endDate", "2019-03-30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RECIPE_TO_MATCHERS.contentJson(getTos(Collections.singletonList(RECIPE2))));
    }

    @Test
    void filterAll() throws Exception {
        perform(doGet("filter?startDate=&endDate=").basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(RECIPE_TO_MATCHERS.contentJson(getTos(RECIPES)));
    }

    @Test
    void createInvalid() throws Exception {
        Recipe invalid = new Recipe(null, "Dummy", 200,null);
        perform(doPost().jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Recipe invalid = new Recipe(RECIPE1_ID, null, 20, List.of(PRODUCT1,PRODUCT2,PRODUCT3));
        perform(doPut(RECIPE1_ID).jsonBody(invalid).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void getWithProducts() throws Exception {
        perform(doGetWithProducts(RECIPE1_ID).basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> PRODUCT_MATCHERS.assertMatch(readFromJsonMvcResult(result, Recipe.class).getProductList(), PRODUCT1,PRODUCT3,PRODUCT2));
    }

    @Test
    void createWithProducts() throws Exception {
        Recipe newRecipe = RecipeTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newRecipe).basicAuth(USER));
        action.andDo(print());
        Recipe created = readFromJson(action, Recipe.class);
        Integer newId = created.getId();
        for (int i=0;i<newRecipe.getProductList().size();i++){
            newRecipe.getProductList().get(i).setId(created.getProductList().get(i).getId());
        }
        newRecipe.setId(created.getId());
        RECIPE_MATCHERS.assertMatch(recipeService.get(newId, USER_ID), newRecipe);
        PRODUCT_MATCHERS.assertMatch(recipeService.get(newId, USER_ID).getProductList(), newRecipe.getProductList());
    }

    @Test
    void updateWithProducts() throws Exception {
        Recipe updated = RecipeTestData.getUpdated();
        perform(doPut(RECIPE1_ID).jsonBody(updated).basicAuth(USER))
                .andExpect(status().isNoContent());
        RECIPE_MATCHERS.assertMatch(recipeService.get(RECIPE1_ID, USER_ID), updated);
        List<Product> products = new ArrayList<>(recipeService.get(RECIPE1_ID, USER_ID).getProductList());
        PRODUCT_MATCHERS.assertMatch(products, updated.getProductList());
    }
}