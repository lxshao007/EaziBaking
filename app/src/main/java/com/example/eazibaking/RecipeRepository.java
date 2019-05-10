package com.example.eazibaking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.Models.RecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private static final String TAG = RecipeRepository.class.getName();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    WebService webService = retrofit.create(WebService.class);

    public LiveData<List<Recipe>> getRecipes() {
        final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
        webService.getRecipes().enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                recipes.setValue(response.body().getRecipes());
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e(TAG, "fetch recipes fail!");
            }
        });
        return recipes;
    }
}