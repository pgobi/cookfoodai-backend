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

    @PostMapping(value = "/generate")
    public Recipe genertateRecipe(@RequestBody Recipe recipe){
        recipeService.newRecipe(recipe);
        return recipe;
    }

    //TO DO



/*
    @PostMapping(value = "/create")
    public Recipe postRecipe(@RequestBody Recipe recipe){
        recipeService.newRecipe(recipe);
        return recipe;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteRecipe(@PathVariable long id){
        if (recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id){
        if (recipeRepository.findById(id).isPresent())
            return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable long id){
        return recipeService.updateRecipe(recipe, id);
    }
 */

    // TO DO
    // RECIPE_GENERATE
}
