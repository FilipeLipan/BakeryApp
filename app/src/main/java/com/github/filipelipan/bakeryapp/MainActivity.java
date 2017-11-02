package com.github.filipelipan.bakeryapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.github.filipelipan.bakeryapp.common.AppActivity;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe.RecipeListFragment;;
import com.github.filipelipan.bakeryapp.modules.recipe_detail.RecipeDetailFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.step.StepFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class MainActivity extends AppActivity {

	@BindView(R.id.container)
	FrameLayout container;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	private Recipe mRecipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		setContainer(container);
		setToolbar(toolbar);

		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(container.getId(), RecipeListFragment.newInstance(), RecipeListFragment.class.getSimpleName());
			ft.commit();
		}

	}

	@Override
	public void setToolbar(Toolbar toolbar) {
		super.setToolbar(toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
