package ru.recipebook.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.recipebook.model.Recipe;
import ru.recipebook.repository.RecipeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaRecipeRepository implements RecipeRepository {

    @Autowired
    private CrudRecipeRepository crudRecipeRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Recipe save(Recipe recipe, int userId) {
        if (!recipe.isNew() && get(recipe.getId(),userId)==null){
            return null;
        }
        recipe.setUser(crudUserRepository.getOne(userId));
        return crudRecipeRepository.save(recipe);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRecipeRepository.delete(id,userId)!=0;
    }

    @Override
    public Recipe get(int id, int userId) {
        return crudRecipeRepository.findById(id).filter(recipe -> recipe.getUser().getId()==userId).orElse(null);
    }

    @Override
    public List<Recipe> getAll(int userId) {
        return crudRecipeRepository.getAll(userId);
    }

    @Override
    public List<Recipe> getBetweenInclusive(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRecipeRepository.getBetween(startDateTime,endDateTime,userId);
    }

    @Override
    public Recipe getWithProducts(int id, int userId) {
        return crudRecipeRepository.getWithProducts(id,userId);
    }
}
