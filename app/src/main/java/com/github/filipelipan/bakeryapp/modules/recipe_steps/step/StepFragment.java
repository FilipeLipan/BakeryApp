package com.github.filipelipan.bakeryapp.modules.recipe_steps.step;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.CardAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.CardFragmentPagerAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.IRecipeStepsView;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lispa on 22/10/2017.
 */

public class StepFragment extends AppFragment<IRecipeStepsView, RecipeStepsPresenter> implements IStepView {

	public static final String STEP_KEY = "STEP_KEY";
	public static final String IS_LAST_ITEM_KEY = "IS_LAST_ITEM_KEY";

	private Step mStep;

	private boolean mIsLastItem = false;

	@BindView(R.id.description_tv)
	TextView descriptionTextView;
	@BindView(R.id.video_iv)
	ImageView videoImageView;
	@BindView(R.id.next_bt)
	Button mNextButton;
	@BindView(R.id.cardView)
	CardView mCardView;
	private StepFragmentListItemListener listener;

	public static StepFragment newInstance(Step step, boolean isLastItem){
		StepFragment stepFragment = new StepFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(STEP_KEY, step);
		bundle.putBoolean(IS_LAST_ITEM_KEY, isLastItem);
		stepFragment.setArguments(bundle);
		return stepFragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.d("", "");
		// This makes sure that the host activity has implemented the callback interface
		// If not, it throws an exception
		try {
			listener = (StepFragmentListItemListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString()
					+ " must implement StepFragmentListItemListener");
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if(getArguments().containsKey(STEP_KEY) && getArguments().containsKey(IS_LAST_ITEM_KEY)){
			mStep = getArguments().getParcelable(STEP_KEY);
			mIsLastItem = getArguments().getBoolean(IS_LAST_ITEM_KEY);

			Glide.with(getContext())
					.load(mStep.getThumbnailURL())
					.apply(RequestOptions.centerCropTransform())
					.into(videoImageView);
			descriptionTextView.setText(mStep.getDescription());

			if(mIsLastItem){
				mNextButton.setText(getContext().getString(R.string.finish));
			}
		}

		mCardView.setMaxCardElevation(mCardView.getCardElevation()
				* CardAdapter.MAX_ELEVATION_FACTOR);

	}

	@OnClick(R.id.next_bt)
	public void onNextClick(){
		if(mIsLastItem){
			listener.onLastStepClick();
		}else {
			listener.onNextStepClick(mStep);
		}
	}

	@Override
	public String getFragmentTag() {
		return StepFragment.class.getSimpleName();
	}

	@Override
	public int getFragmentLayout() {
		return R.layout.step_fragment;
	}

	@Override
	public RecipeStepsPresenter createPresenter() {
		return new RecipeStepsPresenter();
	}

	public CardView getCardView() {
		return mCardView;
	}

	public interface StepFragmentListItemListener {
		void onNextStepClick(Step stepClicked);
		void onLastStepClick();
	}
}
