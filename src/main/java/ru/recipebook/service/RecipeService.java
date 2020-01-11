package ru.recipebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.recipebook.model.Recipe;
import ru.recipebook.repository.RecipeRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static ru.recipebook.util.DateTimeUtil.*;
import static ru.recipebook.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe get(int id, int userId) {
        return checkNotFoundWithId(recipeRepository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(recipeRepository.delete(id, userId), id);
    }

    public List<Recipe> getBetweenDates(@Nullable Date startDate, @Nullable Date endDate, int userId) {
        return recipeRepository.getBetweenInclusive(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    public List<Recipe> getAll(int userId) {
        return recipeRepository.getAll(userId);
    }

    public void update(Recipe recipe, int userId) {
        Assert.notNull(recipe, "recipe must not be null");
        checkNotFoundWithId(recipeRepository.save(recipe, userId), recipe.getId());
    }

    public Recipe create(Recipe recipe, int userId) {
        Assert.notNull(recipe, "recipe must not be null");
        return recipeRepository.save(recipe, userId);
    }

    public Recipe getWithProducts(int id, int userId) {
        return checkNotFoundWithId(recipeRepository.getWithProducts(id, userId), id);
    }
}
