package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.model.ReceiptsResponse;
import com.pgobi.cookfood.ai.repository.RecipeRepository;
import com.pgobi.cookfood.ai.service.OpenAIService;
import com.pgobi.cookfood.ai.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Receipt", description = "The API contains a method generate recipes from AI")
@RestController
@RequestMapping(value = "/api/recipe")
public class RecipeController {

    private final OpenAIService openAIService;

    private RecipeService recipeService;

    public RecipeController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/getAI")
    public ReceiptsResponse getRecipesAI(@RequestParam String category, @RequestParam Integer people){
        return openAIService.getReceiptsAI(category, people);
    }


}
