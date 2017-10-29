package com.github.filipelipan.bakeryapp.modules.recipe_steps.step;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.common.AppFragment;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.CardAdapter;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.IRecipeStepsView;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsPresenter;

import butterknife.BindView;

/**
 * Created by lispa on 22/10/2017.
 */

public class StepFragment extends AppFragment<IRecipeStepsView, RecipeStepsPresenter> implements IStepView {

	public static final String STEP_KEY = "STEP_KEY";

	private Step mStep;

	@BindView(R.id.description_tv)
	TextView descriptionTextView;
	@BindView(R.id.video_iv)
	ImageView videoImageView;
	@BindView(R.id.cardView)
	CardView mCardView;

	public static StepFragment newInstance(Step step){
		StepFragment stepFragment = new StepFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(STEP_KEY, step);
		stepFragment.setArguments(bundle);
		return stepFragment;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if(getArguments().containsKey(STEP_KEY)){
			mStep = getArguments().getParcelable(STEP_KEY);

			Glide.with(getContext())
					.load(mStep.getThumbnailURL())
					.apply(RequestOptions.centerCropTransform())
					.into(videoImageView);
			descriptionTextView.setText(mStep.getDescription());
		}

		mCardView.setMaxCardElevation(mCardView.getCardElevation()
				* CardAdapter.MAX_ELEVATION_FACTOR);


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
}
