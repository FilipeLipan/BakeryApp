package com.github.filipelipan.bakeryapp.modules.recipe_steps;

/**
 * Created by lispa on 22/10/2017.
 */

import android.support.v7.widget.CardView;

public interface CardAdapter {

	int MAX_ELEVATION_FACTOR = 8;

	float getBaseElevation();

	CardView getCardViewAt(int position);

	int getCount();
}