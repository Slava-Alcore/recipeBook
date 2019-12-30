package ru.recipebook.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.recipebook.model.Recipe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.recipebook.util.DateTimeUtil.*;

public interface RecipeRepository {
    Recipe save(Recipe meal, int userId);

    boolean delete(int id, int userId);

    Recipe get(int id, int userId);

    List<Recipe> getAll(int userId);

    default List<Recipe> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return getBetweenInclusive(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    List<Recipe> getBetweenInclusive(@NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate, int userId);

    default Recipe getWithProducts(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
