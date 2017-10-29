package com.github.filipelipan.bakeryapp.modules.recipe_steps;

import com.github.filipelipan.bakeryapp.common.AppView;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by lispa on 15/10/2017.
 */

public interface IRecipeStepsView extends AppView {

	void moveToStep(Step step);
	void onClickLastStep();
}
