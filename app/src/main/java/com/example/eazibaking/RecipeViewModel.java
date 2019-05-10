package com.example.eazibaking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.eazibaking.Models.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private static RecipeRepository recipeRepository;

    public RecipeViewModel() {
        recipeRepository = new RecipeRepository();
    }

    public static LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getRecipes();
    }
}
