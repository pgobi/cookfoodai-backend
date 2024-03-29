package com.pgobi.cookfood.ai.model;

import java.util.List;

public class Receipt {

    private String name;
    private String category;
    private List<Ingredient> ingredients;
    private String preparation;

    public Receipt() {
        // Default constructor
    }

    public Receipt(String name, String category, List<Ingredient> ingredients, String preparation) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.preparation = preparation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
