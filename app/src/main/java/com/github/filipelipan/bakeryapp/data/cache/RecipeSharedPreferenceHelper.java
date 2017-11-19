package com.github.filipelipan.bakeryapp.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecipeSharedPreferenceHelper {
    private static final String STORAGE = "STORAGE";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    public RecipeSharedPreferenceHelper(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public Recipe getStoragePreference() {
        Gson gson = new Gson();
        String exercise = mSharedPreferences.getString(STORAGE, "");
        return gson.fromJson(exercise, Recipe.class);
    }

    public void setSharedPreferences(Recipe recipe) {
        Gson gson = new Gson();
        mEditor.putString(STORAGE, gson.toJson(recipe));
        mEditor.apply();
    }

    public void clearSharedPreferences(){
        mEditor.clear();
        mEditor.commit();
    }
}