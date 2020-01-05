package ru.recipebook.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.recipebook.model.Product;
import ru.recipebook.model.Recipe;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRecipeRepository extends JpaRepository<Recipe, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Recipe r WHERE r.id=:id AND r.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Recipe save(Recipe item);

    @Query("SELECT r FROM Recipe r WHERE r.user.id=:userId ORDER BY r.date DESC")
    List<Recipe> getAll(@Param("userId") int userId);

    @Query("SELECT r from Recipe r WHERE r.user.id=:userId AND r.date >= :startDate AND r.date <= :endDate ORDER BY r.date DESC")
    List<Recipe> getBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"productList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Recipe r WHERE r.id=?1 and r.user.id = ?2")
    Recipe getWithProducts(int id, int userId);
}
