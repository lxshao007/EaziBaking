package com.example.eazibaking;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.databinding.FragmentHomeBinding;
import com.example.eazibaking.databinding.FragmentRecipeDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecipeRepository recipeRepository = new RecipeRepository();
    private static final String TAG = HomeFragment.class.getName();
    private Recipe recipe = new Recipe();
    private RecipeViewModel recipeViewModel;
    private RecipeListAdapter mAdapter;
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_recipe);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, recipes -> {
            mAdapter = new RecipeListAdapter(recipes);
            mRecyclerView.setAdapter(mAdapter);
        });
    }
}
