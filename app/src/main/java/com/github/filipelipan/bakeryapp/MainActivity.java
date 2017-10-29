package com.github.filipelipan.bakeryapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.github.filipelipan.bakeryapp.common.AppActivity;
import com.github.filipelipan.bakeryapp.data.model.Step;
import com.github.filipelipan.bakeryapp.modules.recipe.RecipeListFragment;;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.RecipeStepsFragment;
import com.github.filipelipan.bakeryapp.modules.recipe_steps.step.StepFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppActivity implements StepFragment.StepFragmentListItemListener {

	@BindView(R.id.container)
	FrameLayout container;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		setContainer(container);
		setToolbar(toolbar);

		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(container.getId(), new RecipeListFragment(), RecipeListFragment.class.getSimpleName());
			ft.commit();
		}

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

//	@Override
//	public void onBackPressed() {
//		int count = getSupportFragmentManager().getBackStackEntryCount();
//
//		if (count > 0) {
//			super.onBackPressed();
//			setAsMainView();
//		} else {
//			List<Fragment> fragments = getSupportFragmentManager().getFragments();
//			int size = fragments.size();
//			if (size > 0) {
//				for (int i = size; i > 0; i--) {
//					Fragment fragment = fragments.get(i - 1);
//					if (fragment != null) {
//						if (fragment.getTag() == null || !fragment.getTag().equals(RecipeListFragment.class.getSimpleName())) {
//							drawerCustomizer.initialFragment();
//						} else {
//							super.onBackPressed();
//						}
//						break;
//					}
//				}
//			} else {
//				super.onBackPressed();
//			}
//		}
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
