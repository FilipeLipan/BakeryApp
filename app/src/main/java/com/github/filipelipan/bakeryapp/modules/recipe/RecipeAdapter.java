package com.github.filipelipan.bakeryapp.modules.recipe;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.data.model.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lispa on 14/10/2017.
 */

public class RecipeAdapter extends BaseQuickAdapter<Recipe,BaseViewHolder> {
	private Context mContext;

	public RecipeAdapter(@Nullable List<Recipe> data, Context context) {
		super(R.layout.recipe_list_item, data);
		this.mContext = context;
	}

	@Override
	protected void convert(BaseViewHolder helper, Recipe item) {


		Glide.with(mContext)
				.load(item.getImage())
				.into((ImageView) helper.getView(R.id.recipe_image_iv));

		helper.setText(R.id.recipe_title_tv, item.getName());
		helper.setText(R.id.recipe_description_tv, item.getServings() + mContext.getString(R.string.servings));
	}
}
