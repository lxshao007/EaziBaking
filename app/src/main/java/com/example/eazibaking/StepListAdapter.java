package com.example.eazibaking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eazibaking.Models.Ingredient;
import com.example.eazibaking.Models.Step;
import com.example.eazibaking.databinding.ListItemStepCardBinding;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private static final String TAG = StepListAdapter.class.getName();

    private List<Step> steps;
    private List<Ingredient> ingredients;
    private Context context;
    private boolean twoPane;

    public static final int VIEW_TYPE_INGREDIENT = 0;
    public static final int VIEW_TYPE_STEP = 1;

    StepListAdapter(List<Ingredient> ingredients, List<Step> steps, Context context, Boolean twoPane) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.context = context;
        this.twoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListItemStepCardBinding databinding = ListItemStepCardBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(databinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        Log.i(TAG, "position is" + i);
        switch (type) {
            case VIEW_TYPE_INGREDIENT:
                viewHolder.bind(ingredients.get(i), i + 1, context, twoPane);
                break;
            case VIEW_TYPE_STEP:
                int num = i - ingredients.size();
                viewHolder.bind(steps.get(num), num + 1, context, twoPane);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < ingredients.size()) {
            return VIEW_TYPE_INGREDIENT;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemStepCardBinding databinding;
        private OnStepSelectedListener listener;

        public ViewHolder(@NonNull ListItemStepCardBinding databinding) {
            super(databinding.getRoot());
            this.databinding = databinding;

        }

        public void bind(Step step, int num, Context context,  boolean twoPane) {
            databinding.tvStepName.setText("Step " + num);
            databinding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StepDetailFragment stepDetailFragment = StepDetailFragment
                            .newInstance(ModelUtils.toString(step, new TypeToken<Step>(){}));
                    if (context != null) {
                        if (twoPane) {
                            databinding.cardView.setCardBackgroundColor(R.style.CardView_Dark);
                            listener.onStepSelected(num, getItemViewType());
                        } else {
                            navToFragment(context, stepDetailFragment);
                        }

                    }
                }
            });
        }

        public void bind(Ingredient ingredient, int num, @NonNull Context context, boolean twoPane) {
            databinding.tvStepName.setText("Ingredient " + num + ":" + ingredient.getIngredient());
            databinding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IngredientDetailFragment ingredientDetailFragment = IngredientDetailFragment
                            .newInstance(ModelUtils.toString(ingredient, new TypeToken<Ingredient>(){}));
                    if (context != null) {
                        if (twoPane) {
                            databinding.cardView.setCardBackgroundColor(R.style.CardView_Dark);
                            listener.onStepSelected(num, getItemViewType());
                        } else {
                            navToFragment(context, ingredientDetailFragment);
                        }
                    }
                }
            });
        }

        private void navToFragment(Context context, Fragment fragment) {
            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_step_placeholder, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public interface OnStepSelectedListener {
         void onStepSelected(int id, int viewType);
    }
}
