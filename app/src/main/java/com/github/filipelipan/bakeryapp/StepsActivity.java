package com.github.filipelipan.bakeryapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.filipelipan.bakeryapp.application.BakeryApp;
import com.github.filipelipan.bakeryapp.common.AppActivity;
import com.github.filipelipan.bakeryapp.data.model.Ingredient;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe.RecipeListFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_detail.RecipeDetailFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.step.StepFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class StepsActivity extends AppActivity implements StepFragment.StepFragmentListItemListener {

	@BindView(R.id.container)
	FrameLayout container;
	@Nullable
	@BindView(R.id.tablet_menu_container)
	FrameLayout tabletMenuContainer;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	private Recipe mRecipe;

	public static final String RECIPE_KEY = "recipe";

	public static Intent newIntent(Context context,Recipe recipe){
		Intent intent = new Intent(context, StepsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(RECIPE_KEY, recipe);
		intent.putExtras(bundle);
		return intent;
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_steps);
		ButterKnife.bind(this);

		setContainer(container);
		if(getResources().getBoolean(R.bool.is_tablet)){
			setTabletPanelContainer(tabletMenuContainer);
		}

		if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(RECIPE_KEY)){
			mRecipe = getIntent().getExtras().getParcelable(RECIPE_KEY);
			BakeryApp.getsInstance().getRecipeSharedPreferenceHelper().setSharedPreferences(mRecipe);
			updateWidget();
		}

		setToolbar(toolbar);
//		Step step = new Step().setId(1).setShortDescription("asdasdas").setShortDescription("asdasdasdasd asd as das").setVideoURL("asdasdas").setThumbnailURL("https://i.imgur.com/brMJWS3.jpg");
//				ArrayList<Step> steps = new ArrayList<>();
//				steps.add(step);
//
//				Ingredient ingredient = new Ingredient().setQuantity(1).setMeasure("teste").setIngredient("testeIngredient");
//				ArrayList<Ingredient> ingredients = new ArrayList<>();
//				ingredients.add(ingredient);
//
//		mRecipe = new Recipe().setId(1).setName("teste").setServings(1).setImage("https://i.imgur.com/brMJWS3.jpg").setSteps(steps).setIngredients(ingredients);
		if (savedInstanceState == null && mRecipe != null) {
			replaceFragment(RecipeDetailFragment.newInstance(mRecipe));
		}
	}

	public void updateWidget(){
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsListWidget.class));
		//Trigger data update to handle the GridView widgets and force a data refresh
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
		//Now update all widgets
		IngredientsListWidget.updateRecipeListWidgets(this, appWidgetManager,appWidgetIds);
	}

	@Override
	public void onNextStepClick(Step stepClicked) {
		RecipeStepsFragment recipeStepsFragment =
				(RecipeStepsFragment) getSupportFragmentManager().findFragmentByTag(RecipeStepsFragment.class.getSimpleName());
		recipeStepsFragment.moveToStep(stepClicked);
	}

	@Override
	public void onLastStepClick() {
		RecipeStepsFragment recipeStepsFragment =
				(RecipeStepsFragment) getSupportFragmentManager().findFragmentByTag(RecipeStepsFragment.class.getSimpleName());
		recipeStepsFragment.onClickLastStep();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if(getResources().getBoolean(R.bool.is_tablet)){
			finish();
		}else {
			super.onBackPressed();
		}
	}


}
