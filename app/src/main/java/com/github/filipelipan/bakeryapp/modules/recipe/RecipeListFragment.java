package com.github.filipelipan.bakeryapp.modules.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.application.BakeryApp;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Recipe;

import java.util.ArrayList;

/**
 * Created by lispa on 12/10/2017.
 */

public class RecipeListFragment extends AppFragment<IRecipeListView, RecipeListPresenter> implements IRecipeListView {

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getPresenter().getRecipes();
	}

	@Override
	public String getFragmentTag() {
		return RecipeListFragment.class.getSimpleName();
	}

	@Override
	public int getFragmentLayout() {
		return R.layout.recipe_list_fragment;
	}

	@Override
	public RecipeListPresenter createPresenter() {
		return new RecipeListPresenter(BakeryApp.getsInstance().getRestApi());
	}

	@Override
	public void loadRecipes(ArrayList<Recipe> recipes) {

	}
}
