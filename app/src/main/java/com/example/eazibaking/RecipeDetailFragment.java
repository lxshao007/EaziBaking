package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.databinding.FragmentRecipeDetailBinding;
import com.google.gson.reflect.TypeToken;

public class RecipeDetailFragment extends Fragment {

    private FragmentRecipeDetailBinding binding;
    private Recipe recipe;
    private StepListAdapter mAdapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String recipeString = getArguments().getString("recipe");
        recipe = ModelUtils.toObject(recipeString, new TypeToken<Recipe>(){});
        RecyclerView mRecyclerView = binding.rvRecipeDetail;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new StepListAdapter(recipe.getIngredients(), recipe.getSteps(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(recipe.getName());
    }
}
