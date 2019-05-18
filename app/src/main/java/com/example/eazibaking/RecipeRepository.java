package com.example.eazibaking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.eazibaking.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepository {
    private static final String TAG = RecipeRepository.class.getName();

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static WebService webService = retrofit.create(WebService.class);

    public static LiveData<List<Recipe>> getRecipes() {
        final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
        webService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "fetch recipes fail! error is" + t.toString());
            }
        });
        return recipes;
    }
}
