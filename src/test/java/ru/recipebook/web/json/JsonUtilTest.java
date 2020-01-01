package ru.recipebook.web.json;

import org.junit.jupiter.api.Test;
import ru.recipebook.UserTestData;
import ru.recipebook.model.Recipe;
import ru.recipebook.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.recipebook.RecipeTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_RECIPE);
        System.out.println(json);
        Recipe recipe = JsonUtil.readValue(json, Recipe.class);
        RECIPE_MATCHERS.assertMatch(recipe, ADMIN_RECIPE);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(RECIPES);
        System.out.println(json);
        List<Recipe> meals = JsonUtil.readValues(json, Recipe.class);
        RECIPE_MATCHERS.assertMatch(meals, RECIPES);
    }

    @Test
    void writeOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = JsonUtil.writeAdditionProps(UserTestData.USER, "password", "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}