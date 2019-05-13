package com.example.eazibaking;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eazibaking.Models.Ingredient;
import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.Models.Step;
import com.example.eazibaking.databinding.ActivityStepBinding;
import com.google.gson.reflect.TypeToken;

public class RecipeDetailActivity extends AppCompatActivity implements StepListAdapter.OnStepSelectedListener {

    private ActivityStepBinding databinding;
    private boolean mTwoPane;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_step);
        mTwoPane = databinding.fragmentStepDetailPlaceholder == null ? false : true;
        String recipeString = getIntent().getStringExtra("recipe");
        recipe = ModelUtils.toObject(recipeString, new TypeToken<Recipe>(){});
        if (savedInstanceState == null) {
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipeString, mTwoPane);
            navToFragment(R.id.fragment_step_placeholder, recipeDetailFragment);
            if (mTwoPane) {
                IngredientDetailFragment ingredientDetailFragment = IngredientDetailFragment
                        .newInstance(ModelUtils.toString(recipe.getIngredients().get(0), new TypeToken<Ingredient>() {
                        }));
                navToFragment(R.id.fragment_step_detail_placeholder, ingredientDetailFragment);
            }
        }
    }

    private void navToFragment(int id, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
    }

    //TODO NOT WORKING
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onStepSelected(int id, int viewType) {
        if (viewType == StepListAdapter.VIEW_TYPE_INGREDIENT) {
            IngredientDetailFragment ingredientDetailFragment = IngredientDetailFragment
                    .newInstance(ModelUtils.toString(recipe.getIngredients().get(id), new TypeToken<Ingredient>(){}));
            navToFragment(R.id.fragment_step_detail_placeholder, ingredientDetailFragment);
        } else {
            StepDetailFragment stepDetailFragment = StepDetailFragment
                    .newInstance(ModelUtils.toString(recipe.getSteps().get(id), new TypeToken<Step>(){}));
            navToFragment(R.id.fragment_step_detail_placeholder, stepDetailFragment);
        }
    }
}
