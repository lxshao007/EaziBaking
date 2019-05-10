package com.example.eazibaking;

import com.example.eazibaking.Models.RecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<RecipeResponse> getRecipes();
}
