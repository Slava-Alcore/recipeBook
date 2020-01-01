package ru.recipebook.web.recipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.recipebook.model.Recipe;
import ru.recipebook.service.RecipeService;
import ru.recipebook.to.RecipeTo;
import ru.recipebook.util.RecipeUtil;
import ru.recipebook.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.recipebook.util.ValidationUtil.assureIdConsistent;
import static ru.recipebook.util.ValidationUtil.checkNew;

public abstract class AbstractRecipeController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RecipeService service;

    public Recipe get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get recipe {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete recipe {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<RecipeTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return RecipeUtil.getTos(service.getAll(userId));
    }

    public Recipe create(Recipe recipe) {
        int userId = SecurityUtil.authUserId();
        checkNew(recipe);
        log.info("create {} for user {}", recipe, userId);
        return service.create(recipe, userId);
    }

    public void update(Recipe recipe, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(recipe, id);
        log.info("update {} for user {}", recipe, userId);
        service.update(recipe, userId);
    }

    public List<RecipeTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Recipe> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return RecipeUtil.getFilteredTos(mealsDateFiltered, startTime, endTime);
    }
}