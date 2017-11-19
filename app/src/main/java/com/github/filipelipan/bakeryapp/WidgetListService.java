package com.github.filipelipan.bakeryapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.filipelipan.bakeryapp.data.cache.RecipeSharedPreferenceHelper;
import com.github.filipelipan.bakeryapp.data.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by lispa on 19/11/2017.
 */

public class WidgetListService extends RemoteViewsService {
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new ListRemoteViewsFactory(this.getApplicationContext());
	}
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	Context mContext;
	ArrayList<Ingredient> mIngredients;

	public ListRemoteViewsFactory(Context context) {
		mContext = context;
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {
		RecipeSharedPreferenceHelper recipeSharedPreferenceHelper = new RecipeSharedPreferenceHelper(mContext);
		mIngredients = recipeSharedPreferenceHelper.getRecipeStoragePreference().getIngredients();

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		if(mIngredients == null) return 0;
		return mIngredients.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		if(mIngredients == null || mIngredients.size() == 0) return null;

		RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_list_item);
		views.setTextViewText(R.id.name_tv, String.valueOf(mIngredients.get(position).getIngredient()));

		return views;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}