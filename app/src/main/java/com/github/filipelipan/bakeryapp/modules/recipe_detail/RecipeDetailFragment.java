package com.github.filipelipan.bakeryapp.modules.recipe_detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Ingredient;
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
	@BindView(R.id.recipe_name_tv)
	TextView mRecipeName;

	@BindView(R.id.recipe_image_kbv)
	KenBurnsView mRecipeImage;

	public static final String HIDE_ANIMATION_KEY = "hide-animation-key";
	private boolean mAnimationKey = false;

	public static RecipeDetailFragment newInstance(Recipe recipe, boolean animationKey){
		RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(RECIPE_KEY, recipe);
		bundle.putBoolean(HIDE_ANIMATION_KEY, animationKey);
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
				mAnimationKey = bundle.getBoolean(HIDE_ANIMATION_KEY);
				if(mAnimationKey){
					mRecipeImage.setVisibility(View.GONE);
				}

				getAppActivityListener().setTitle(mRecipe.getName());

				Glide.with(getContext())
						.load(mRecipe.getImage())
						.thumbnail(Glide.with(getContext())
								.load(R.drawable.recipe_placeholder))
						.into((ImageView) mRecipeImage);
				mRecipeName.setText(mRecipe.getName());
			}
		}

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

		mIngredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients(), getContext());

		mIngredientsAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.empty_list, mIngredientsRecyclerView));
		mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
		mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mIngredientsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("", ((Ingredient) adapter.getItem(position)).getIngredient());
				clipboard.setPrimaryClip(clip);

				Toast.makeText(getContext(), getString(R.string.copy_to_clipboard), Toast.LENGTH_SHORT).show();
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

	@Override
	public boolean isTabletPanel() {
		return true;
	}
}
