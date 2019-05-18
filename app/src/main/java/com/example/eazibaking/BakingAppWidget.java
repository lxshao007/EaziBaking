package com.example.eazibaking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.example.eazibaking.Models.Ingredient;
import com.example.eazibaking.Models.Recipe;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String recipeString = sharedPreferences.getString("recipe", "");
        Recipe recipe = null;
        List<Ingredient> ingredients = Collections.emptyList();
        if (!TextUtils.isEmpty((recipeString))) {
            recipe = ModelUtils.toObject(recipeString, new TypeToken<Recipe>(){});
            ingredients = recipe.getIngredients();
        }

        String name = recipe == null ? "Cookie:" : recipe.getName() + ":";
        for (int i = 0; i < ingredients.size(); i ++) {
            int step = i + 1;
            name += "\n" + step + ". " + ingredients.get(i).getIngredient();
        }
        views.setTextViewText(R.id.appwidget_text, name);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_view, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

