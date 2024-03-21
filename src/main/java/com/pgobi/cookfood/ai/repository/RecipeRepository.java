package com.pgobi.cookfood.ai.repository;

import com.pgobi.cookfood.ai.enums.RecipeCategory;
import com.pgobi.cookfood.ai.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByRecipeCategory(RecipeCategory recipeCategory);
}
