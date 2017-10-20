package com.github.filipelipan.bakeryapp.modules.recipe_steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.modules.recipe.IRecipeListView;

import java.util.ArrayList;

/**
 * Created by lispa on 15/10/2017.
 */

public class RecipeStepsFragment extends AppFragment<IRecipeStepsView, RecipeStepsPresenter> implements IRecipeStepsView {


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public String getFragmentTag() {
		return RecipeStepsFragment.class.getSimpleName();
	}

	@Override
	public int getFragmentLayout() {
		return R.layout.recipe_steps_fragment;
	}

	@Override
	public RecipeStepsPresenter createPresenter() {
		return new RecipeStepsPresenter();
	}
}
