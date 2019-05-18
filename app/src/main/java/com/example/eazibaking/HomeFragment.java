package com.example.eazibaking;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.databinding.FragmentHomeBinding;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.eazibaking.MainActivity.getIdleResource;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();
    private static final String ARG_TWO_PANE = "ARG_TWO_PANE";
    private RecipeViewModel recipeViewModel;
    private RecipeListAdapter mAdapter;
    private FragmentHomeBinding binding;
    private boolean twoPane;
    private List<Recipe> recipeList;

    public static HomeFragment newInstance(boolean twoPane) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_TWO_PANE, twoPane);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            twoPane = getArguments().getBoolean(ARG_TWO_PANE);
        }
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
        RecyclerView mRecyclerView = binding.rvRecipe;
        if (twoPane) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        RecipeViewModel.getRecipes().observe(this, recipes -> {
            recipeList = recipes;
            mAdapter = new RecipeListAdapter(recipes);
            mRecyclerView.setAdapter(mAdapter);
            if (recipeList != null && recipeList.size() > 0) {
                editor.putString("recipe", ModelUtils.toString(recipeList.get(0), new TypeToken<Recipe>(){}));
                editor.apply();
            }
        });
    }
}
