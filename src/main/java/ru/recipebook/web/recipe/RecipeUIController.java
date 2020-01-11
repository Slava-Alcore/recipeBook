package ru.recipebook.web.recipe;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.recipebook.View;
import ru.recipebook.model.Recipe;
import ru.recipebook.to.RecipeTo;
import ru.recipebook.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/recipes")
public class RecipeUIController extends AbstractRecipeController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecipeTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}")
    public Recipe get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/withprod/{id}")
    public Recipe getWithProducts(@PathVariable int id) {
        return super.getWithProducts(id);
    }


    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class)
                                   @RequestBody Recipe recipe) {
        if (recipe.isNew()) {
            super.create(recipe);
        } else {
            super.update(recipe, recipe.getId());
        }
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecipeTo> getBetween(
            @RequestParam @Nullable @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN) Date startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN) Date endDate) {
        return super.getBetween(startDate, endDate);
    }
}