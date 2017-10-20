package com.github.filipelipan.bakeryapp.modules.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.application.BakeryApp;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.modules.recipe_detail.RecipeDetailFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by lispa on 12/10/2017.
 */

public class RecipeListFragment extends AppFragment<IRecipeListView, RecipeListPresenter> implements IRecipeListView {


	@BindView(R.id.recipe_list_rv)
	RecyclerView mRecipeRecyclerView;

	private RecipeAdapter mRecipeAdapter;

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getPresenter().getRecipes();

		initAdapter();
	}

	private void initAdapter() {
		mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(), getContext());


		mRecipeAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.empty_list, mRecipeRecyclerView));
		mRecipeRecyclerView.setAdapter(mRecipeAdapter);
		mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mRecipeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				getAppActivityListener().replaceAndBackStackFragment(RecipeDetailFragment.newInstance((Recipe) adapter.getItem(position)));
			}
		});
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
		mRecipeAdapter.setNewData(recipes);
	}
}
