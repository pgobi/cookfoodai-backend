package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Recipe;
import com.pgobi.cookfood.ai.enums.RecipeCategory;
import com.pgobi.cookfood.ai.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeCategory findCategory(String category) {
        RecipeCategory recipeCategory = RecipeCategory.BREAKFAST;
        if(category.equals("1"))
            recipeCategory = RecipeCategory.LUNCH;
        if(category.equals("2"))
            recipeCategory = RecipeCategory.DINNER;

        return recipeCategory;
    }

    public void newRecipe(Recipe recipe) {
        recipe.setDateCreated(new Date());
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(long id) {
        Recipe recipeToSave = recipeRepository.findById(id).orElse(null);
        Recipe recipeToReturn = new Recipe();

        if (recipeToSave != null) {
            recipeToReturn.setRecipeId(recipeToSave.getRecipeId());
            copyRecipe(recipeToReturn, recipeToSave);
            recipeToSave.setDateCreated(new Date());
            recipeRepository.save(recipeToSave);
            return recipeToReturn;
        }
        return null;
    }

    public Recipe updateRecipe(Recipe recipe, long id) {
        Optional<Recipe> recipeToCheck = recipeRepository.findById(id);

        if (recipeToCheck.isPresent()) {
            Recipe recipeToSave = recipeToCheck.get();
            copyRecipe(recipeToSave, recipe);

            recipeRepository.save(recipeToSave);
            return recipeToSave;
        } else {
            return null;
        }
    }

    private void copyRecipe(Recipe recipe1, Recipe recipe2) {
        recipe1.setRecipeName(recipe2.getRecipeName());
        recipe1.setRecipeCategory(recipe2.getRecipeCategory());
        recipe1.setInstructions(recipe2.getInstructions());
        recipe1.setDateCreated(recipe2.getDateCreated());
    }

}
