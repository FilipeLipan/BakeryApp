package com.github.filipelipan.bakeryapp.modules.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.StepsActivity;
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

	public static RecipeListFragment newInstance(){
		return new RecipeListFragment();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getPresenter().getRecipes();

		initAdapter();
	}

	private void initAdapter() {
		mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(), getContext());


		mRecipeAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.empty_list, mRecipeRecyclerView));
		mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), calculateNoOfColumns(getContext())));
		mRecipeRecyclerView.setAdapter(mRecipeAdapter);

		mRecipeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				startActivity(StepsActivity.newIntent(getContext(), (Recipe) adapter.getItem(position)));
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
		return new RecipeListPresenter(BakeryApp.getsInstance().getRecipeRepository());
	}

	@Override
	public void loadRecipes(ArrayList<Recipe> recipes) {
		mRecipeAdapter.setNewData(recipes);
	}

	@Override
	public boolean isTabletPanel() {
		return true;
	}

	/**
	 * Calculate the number of columns for the Gridview
	 *
	 * @param context Used to access the DisplayMetrics
	 * @return An int resulting from the division between the screen width and a given dp.
	 */
	public static int calculateNoOfColumns(Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
		int noOfColumns = 0;

		if(context.getResources().getBoolean(R.bool.is_tablet)){
			noOfColumns = (int) (dpWidth / 270);
		}else {
			noOfColumns = (int) (dpWidth / 250);
		}

		if(noOfColumns <= 0){
			noOfColumns = 1;
		}
		return noOfColumns;
	}
}
