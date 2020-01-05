package ru.recipebook.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.recipebook.UserTestData.ADMIN;
import static ru.recipebook.UserTestData.USER;

class RootControllerTest extends AbstractControllerTest {

    RootControllerTest() {
        super("");
    }

    @Test
    void getUsers() throws Exception {
        perform(doGet("users").auth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void unAuth() throws Exception {
        perform(doGet("users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getRecipes() throws Exception {
        perform(doGet("recipes").auth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipes"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/recipes.jsp"));
    }
}