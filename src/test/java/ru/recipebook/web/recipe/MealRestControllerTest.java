package ru.recipebook.web.recipe;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.recipebook.RecipeTestData;
import ru.recipebook.model.Recipe;
import ru.recipebook.service.RecipeService;
import ru.recipebook.util.exception.NotFoundException;
import ru.recipebook.web.AbstractControllerTest;

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

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RecipeService recipeService;

    MealRestControllerTest() {
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
        assertThrows(NotFoundException.class, () -> recipeService.get(RECIPE1_ID, USER_ID));
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
        perform(doPut(RECIPE1_ID).jsonBody(updated).basicAuth(USER))
                .andExpect(status().isNoContent());

        RECIPE_MATCHERS.assertMatch(recipeService.get(RECIPE1_ID, RECIPE_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Recipe newMeal = RecipeTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newMeal).basicAuth(USER));

        Recipe created = readFromJson(action, Recipe.class);
        Integer newId = created.getId();
        newMeal.setId(newId);
        RECIPE_MATCHERS.assertMatch(created, newMeal);
        RECIPE_MATCHERS.assertMatch(recipeService.get(newId, USER_ID), newMeal);
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
                .param("startDate", "2019-03-30").param("startTime", "07:00")
                .param("endDate", "2019-03-30").param("endTime", "14:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RECIPE_TO_MATCHERS.contentJson(createTo(RECIPE2)));
    }

    @Test
    void filterAll() throws Exception {
        perform(doGet("filter?startDate=&endTime=").basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(RECIPE_TO_MATCHERS.contentJson(getTos(RECIPES)));
    }

    @Test
    void createInvalid() throws Exception {
        Recipe invalid = new Recipe(null, null, "Dummy", 200);
        perform(doPost().jsonBody(invalid).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Recipe invalid = new Recipe(RECIPE1_ID, null, null, 20);
        perform(doPut(RECIPE1_ID).jsonBody(invalid).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}