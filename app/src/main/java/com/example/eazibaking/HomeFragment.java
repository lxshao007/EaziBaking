package com.example.eazibaking;

import android.arch.lifecycle.ViewModelProviders;
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

import com.example.eazibaking.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();
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
        RecyclerView mRecyclerView = binding.rvRecipe;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        RecipeViewModel.getRecipes().observe(this, recipes -> {
            mAdapter = new RecipeListAdapter(recipes);
            mRecyclerView.setAdapter(mAdapter);
        });
    }
}
