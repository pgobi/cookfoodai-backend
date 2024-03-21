package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.entities.Recipe;
import com.pgobi.cookfood.ai.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Recipe", description = "The API contains a method generate recipes from AI")
@RestController
@RequestMapping(value = "/api/recipe")
@CrossOrigin
public class RecipeController {

    private RecipeService recipeService;


    // TO DO
    // RECIPE_GENERATE

    @PostMapping(value = "/generate")
    public Recipe genertateRecipe(@RequestBody Recipe recipe){
        recipeService.newRecipe(recipe);
        return recipe;
    }

}
