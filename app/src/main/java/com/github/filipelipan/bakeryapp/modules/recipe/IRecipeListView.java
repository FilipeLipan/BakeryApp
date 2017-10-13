package com.github.filipelipan.bakeryapp.modules.recipe;

import com.github.filipelipan.bakeryapp.common.AppView;
import com.github.filipelipan.bakeryapp.data.model.Recipe;

import java.util.ArrayList;


/**
 * Created by lispa on 12/10/2017.
 */

public interface IRecipeListView extends AppView {
	void loadRecipes(ArrayList<Recipe> recipes);
}
