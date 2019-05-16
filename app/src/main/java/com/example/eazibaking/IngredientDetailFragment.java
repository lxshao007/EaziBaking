package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Ingredient;
import com.example.eazibaking.databinding.FragmentIngredientDetailBinding;
import com.google.gson.reflect.TypeToken;

public class IngredientDetailFragment extends Fragment {

    private static final String ARG_INGREDIENT = "ARG_INGREDIENT";

    private String arg_ingredient;
    private FragmentIngredientDetailBinding databinding;
    private Ingredient ingredient;

    public IngredientDetailFragment() {
        // Required empty public constructor
    }

    public static IngredientDetailFragment newInstance(String param1) {
        IngredientDetailFragment fragment = new IngredientDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INGREDIENT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arg_ingredient = getArguments().getString(ARG_INGREDIENT);
            ingredient = ModelUtils.toObject(arg_ingredient, new TypeToken<Ingredient>(){});
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_detail, container,false);
        databinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
        databinding.tvMeasure.setText(ingredient.getMeasure());
        databinding.tvIngredient.setText(ingredient.getIngredient());
        return databinding.getRoot();
    }
}
