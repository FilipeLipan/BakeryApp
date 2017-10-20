package com.github.filipelipan.bakeryapp.modules.recipe_detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.data.model.Ingredient;
import com.github.filipelipan.bakeryapp.data.model.Step;

import java.util.List;

/**
 * Created by lispa on 15/10/2017.
 */

public class IngredientsAdapter extends BaseQuickAdapter<Ingredient,BaseViewHolder> {
	private Context mContext;

	public IngredientsAdapter(@Nullable List<Ingredient> data, Context context) {
		super(R.layout.ingredients_list_item, data);
		this.mContext = context;
	}

	@Override
	protected void convert(BaseViewHolder helper, Ingredient item) {

		helper.setText(R.id.ingredient_tv, item.getIngredient());
		helper.setText(R.id.quantity_tv, item.getQuantity() + "");
		helper.setText(R.id.measure_tv, item.getMeasure());
	}
}