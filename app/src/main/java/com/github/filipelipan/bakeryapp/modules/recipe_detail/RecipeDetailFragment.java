package com.github.filipelipan.bakeryapp.modules.recipe_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.modules.recipe.RecipeAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by lispa on 15/10/2017.
 */

public class RecipeDetailFragment extends AppFragment<IRecipeDetailView, RecipeDetailPresenter> implements IRecipeDetailView {

	private Recipe mRecipe;
	public static final String RECIPE_KEY = "recipe";

	private IngredientsAdapter mIngredientsAdapter;
	private StepsAdapter mStepsAdapter;

	@BindView(R.id.ingredients_rv)
	RecyclerView mIngredientsRecyclerView;

	@BindView(R.id.steps_rv)
	RecyclerView mStepsRecyclerView;

	public static RecipeDetailFragment newInstance(Recipe recipe){
		RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(RECIPE_KEY, recipe);
		recipeDetailFragment.setArguments(bundle);
		return recipeDetailFragment;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle bundle = getArguments();
		if(bundle != null ){
			if(bundle.containsKey(RECIPE_KEY)){
				mRecipe = bundle.getParcelable(RECIPE_KEY);
			}
		}

		mIngredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients(), getContext());

		mIngredientsAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.empty_list, mIngredientsRecyclerView));
		mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
		mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mIngredientsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				getAppActivityListener().replaceAndBackStackFragment(RecipeDetailFragment.newInstance((Recipe) adapter.getItem(position)));
			}
		});

		mStepsAdapter = new StepsAdapter(mRecipe.getSteps(), getContext());

		mStepsAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.empty_list, mStepsRecyclerView));
		mStepsRecyclerView.setAdapter(mStepsAdapter);
		mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mStepsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				getAppActivityListener().replaceAndBackStackFragment(RecipeStepsFragment.newInstance(mRecipe.getSteps(), position));
			}
		});
	}

	@Override
	public String getFragmentTag() {
		return RecipeDetailFragment.class.getSimpleName();
	}

	@Override
	public int getFragmentLayout() {
		return R.layout.recipe_detail_fragment;
	}

	@Override
	public RecipeDetailPresenter createPresenter() {
		return new RecipeDetailPresenter();
	}
}
