package com.github.filipelipan.bakeryapp.modules.recipe_steps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Step;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by lispa on 15/10/2017.
 */

public class RecipeStepsFragment extends AppFragment<IRecipeStepsView, RecipeStepsPresenter> implements IRecipeStepsView {

	public static final String KEY_STEPS = "KEY_STEPS";
	public static final String KEY_POSITION = "KEY_POSITION";

	@BindView(R.id.viewPager)
	ViewPager mViewPager;

	private CardFragmentPagerAdapter mFragmentCardAdapter;
	private ShadowTransformer mFragmentCardShadowTransformer;

	private ArrayList<Step> mSteps;
	private int mPosition;

	public static RecipeStepsFragment newInstance(ArrayList<Step> steps, int position){
		RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(KEY_STEPS, steps);
		bundle.putInt(KEY_POSITION, position);
		recipeStepsFragment.setArguments(bundle);
		return recipeStepsFragment;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if(getArguments().containsKey(KEY_STEPS) && getArguments().containsKey(KEY_POSITION)){
			mSteps = getArguments().getParcelableArrayList(KEY_STEPS);
			mPosition = getArguments().getInt(KEY_POSITION);
		}

		mFragmentCardAdapter = new CardFragmentPagerAdapter(getFragmentManager(),
				dpToPixels(2, getContext()), mSteps);

		mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(mFragmentCardAdapter);
//		mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);

		mFragmentCardShadowTransformer.enableScaling(true);

		if(mSteps != null && mPosition >= 0) {
			mViewPager.setCurrentItem(mPosition);
		}
	}

	public static float dpToPixels(int dp, Context context) {
		return dp * (context.getResources().getDisplayMetrics().density);
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


	@Override
	public void moveToStep(Step step) {
		int index = 0;
		index = mSteps.indexOf(step);
		mViewPager.setCurrentItem(index + 1);
	}

	@Override
	public void onClickLastStep() {
		getActivity().onBackPressed();
	}
}
