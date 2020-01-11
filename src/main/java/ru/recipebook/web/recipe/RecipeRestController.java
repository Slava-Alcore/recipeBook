package ru.recipebook.web.recipe;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.recipebook.View;
import ru.recipebook.model.Recipe;
import ru.recipebook.to.RecipeTo;
import ru.recipebook.util.DateTimeUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = RecipeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController extends AbstractRecipeController {
    static final String REST_URL = "/rest/profile/recipes";

    @Override
    @GetMapping("/{id}")
    public Recipe get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/withprod/{id}")
    public Recipe getWithProducts(@PathVariable int id) {
        return super.getWithProducts(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<RecipeTo> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Recipe recipe, @PathVariable int id) {
        super.update(recipe, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createWithLocation(@Validated(View.Web.class) @RequestBody Recipe recipe) {
        Recipe created = super.create(recipe);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping(value = "/filter")
    public List<RecipeTo> getBetween(
            @RequestParam @Nullable @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN) Date startDate,
            @RequestParam @Nullable @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN) Date endDate) {
        return super.getBetween(startDate, endDate);
    }
}