package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Receipt;
import com.pgobi.cookfood.ai.enums.Category;
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

    public Category findCategory(String category) {
        Category mealCategory = Category.BREAKFAST;
        if(category.equals("1"))
            mealCategory = Category.LUNCH;
        if(category.equals("2"))
            mealCategory = Category.DINNER;

        return mealCategory;
    }

    public void newRecipe(Receipt receipt) {
        receipt.setDateCreated(new Date());
        recipeRepository.save(receipt);
    }

    public Receipt getRecipe(long id) {
        Receipt receiptToSave = recipeRepository.findById(id).orElse(null);
        Receipt receiptToReturn = new Receipt();

        if (receiptToSave != null) {
            receiptToReturn.setRecipeId(receiptToSave.getRecipeId());
            copyRecipe(receiptToReturn, receiptToSave);
            receiptToSave.setDateCreated(new Date());
            recipeRepository.save(receiptToSave);
            return receiptToReturn;
        }
        return null;
    }

    public Receipt updateRecipe(Receipt receipt, long id) {
        Optional<Receipt> recipeToCheck = recipeRepository.findById(id);

        if (recipeToCheck.isPresent()) {
            Receipt receiptToSave = recipeToCheck.get();
            copyRecipe(receiptToSave, receipt);

            recipeRepository.save(receiptToSave);
            return receiptToSave;
        } else {
            return null;
        }
    }

    //TODO
    private void copyRecipe(Receipt receipt1, Receipt receipt2) {

    }

}
