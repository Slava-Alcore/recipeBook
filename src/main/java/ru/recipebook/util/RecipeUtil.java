package ru.recipebook.util;

import org.springframework.lang.Nullable;
import ru.recipebook.model.Recipe;
import ru.recipebook.to.RecipeTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RecipeUtil {
    private RecipeUtil() {
    }

    public static List<RecipeTo> getTos(Collection<Recipe> recipes) {
        return getFiltered(recipes, recipe -> true);
    }

    public static List<RecipeTo> getFilteredTos(Collection<Recipe> recipes, @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return getFiltered(recipes, recipe -> isBetweenInclusive(recipe.getTime(), startTime, endTime));
    }

    private static List<RecipeTo> getFiltered(Collection<Recipe> recipes, Predicate<Recipe> filter) {
        return recipes.stream()
                .filter(filter)
                .map(RecipeUtil::createTo)
                .collect(Collectors.toList());
    }

    public static RecipeTo createTo(Recipe recipe) {
        return new RecipeTo(recipe.getId(), recipe.getDateTime(), recipe.getDescription(), recipe.getServings(),recipe.getProductList());
    }

    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
}
