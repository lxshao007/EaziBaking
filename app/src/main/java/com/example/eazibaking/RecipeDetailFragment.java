package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Ingredient;
import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.databinding.FragmentRecipeDetailBinding;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RecipeDetailFragment extends Fragment {
    private FragmentRecipeDetailBinding binding;
    public static RecipeDetailFragment newInstance(String recipeString) {
        Bundle args = new Bundle();
        args.putString("recipe", recipeString);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String recipeString = getArguments().getString("recipe");
        Recipe recipe = ModelUtils.toObject(recipeString, new TypeToken<Recipe>(){});

    }
}
