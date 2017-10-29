package com.github.filipelipan.bakeryapp.modules.recipe_steps;

/**
 * Created by lispa on 22/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.step.StepFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

	private List<StepFragment> mFragments;
	private float mBaseElevation;
	private ArrayList<Step> mSteps;

	public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation, ArrayList<Step> steps) {
		super(fm);
		mFragments = new ArrayList<>();
		mBaseElevation = baseElevation;
		mSteps = steps;

		for (Step step: mSteps) {
			StepFragment stepFragment = StepFragment.newInstance(step, false);
			if(mSteps.indexOf(step) == (mSteps.size() -1)){
				stepFragment = StepFragment.newInstance(step, true);
			}
			addCardFragment(stepFragment);
		}

	}

	@Override
	public float getBaseElevation() {
		return mBaseElevation;
	}

	@Override
	public CardView getCardViewAt(int position) {
		return mFragments.get(position).getCardView();
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Object fragment = super.instantiateItem(container, position);
		mFragments.set(position, (StepFragment) fragment);
		return fragment;
	}

	public void addCardFragment(StepFragment fragment) {
		mFragments.add(fragment);
	}

}