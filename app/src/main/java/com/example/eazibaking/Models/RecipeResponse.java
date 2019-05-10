package com.example.eazibaking.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {
    private List<Recipe> recipes;

    public RecipeResponse() {
        recipes = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public static RecipeResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        RecipeResponse recipeResponse = gson.fromJson(response, RecipeResponse.class);
        return recipeResponse;
    }
}
