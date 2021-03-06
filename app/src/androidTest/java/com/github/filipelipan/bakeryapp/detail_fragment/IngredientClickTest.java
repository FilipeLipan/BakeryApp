package com.github.filipelipan.bakeryapp.detail_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.StepsActivity;
import com.github.filipelipan.bakeryapp.data.model.Ingredient;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.data.model.Step;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by lispa on 15/11/2017.
 */

public class IngredientClickTest {
	public static final String TEA_NAME = "Green Tea";

	/**
	 * The ActivityTestRule is a rule provided by Android used for functional testing of a single
	 * activity. The activity that will be tested will be launched before each test that's annotated
	 * with @Test and before methods annotated with @Before. The activity will be terminated after
	 * the test and methods annotated with @After are complete. This rule allows you to directly
	 * access the activity during the test.
	 */
	@Rule
	public ActivityTestRule<StepsActivity> mActivityTestRule = new ActivityTestRule<StepsActivity>(StepsActivity.class) {
			@Override
			protected Intent getActivityIntent() {
				Step step = new Step().setId(1).setShortDescription("asdasdas").setDescription("asdasdasdasd asd as das").setVideoURL("asdasdas").setThumbnailURL("https://i.imgur.com/brMJWS3.jpg");
				ArrayList<Step> steps = new ArrayList<>();
				steps.add(step);

				Ingredient ingredient = new Ingredient().setQuantity(1).setMeasure("teste").setIngredient("testeIngredient");
				ArrayList<Ingredient> ingredients = new ArrayList<>();
				ingredients.add(ingredient);

				Recipe recipe = new Recipe().setId(1).setName("teste").setServings(1).setImage("https://i.imgur.com/brMJWS3.jpg").setSteps(steps).setIngredients(ingredients);

				Context targetContext = InstrumentationRegistry.getInstrumentation()
						.getTargetContext();


				return StepsActivity.newIntent(targetContext, recipe, true);
			}
		};

	/**
	 * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
	 */
	@Test
	public void click_Ingredient_And_Clipboard() {
		onView(withId(R.id.rootScrollView))
				.perform(swipeDown());
		onView(ViewMatchers.withId(R.id.ingredients_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
	}

	@Test
	public void click_steps_and_go_to_detail_screen() {
		onView(withId(R.id.rootScrollView))
				.perform(swipeUp());
		onView(ViewMatchers.withId(R.id.steps_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
		onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
				.check(matches(withText("Steps")));
	}
}
