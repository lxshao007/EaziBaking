package com.example.eazibaking;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Recipe;
import com.example.eazibaking.databinding.ListItemRecipeCardBinding;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private List<Recipe> recipes;

    public RecipeListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListItemRecipeCardBinding databinding = ListItemRecipeCardBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(databinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe recipe = recipes.get(i);
        viewHolder.bind(recipe);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemRecipeCardBinding databinding;

        public ViewHolder(@NonNull ListItemRecipeCardBinding databinding) {
            super(databinding.getRoot());
            this.databinding = databinding;
        }

        public void bind(Recipe recipe) {
            databinding.tvRecipeName.setText(recipe.getName());
            databinding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra("recipe", ModelUtils.toString(recipe, new TypeToken<Recipe>(){}));
                    context.startActivity(intent);
                }
            });
        }
    }
}
