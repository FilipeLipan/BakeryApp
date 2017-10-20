package com.github.filipelipan.bakeryapp.modules.recipe_detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.data.model.Step;

import java.util.List;

/**
 * Created by lispa on 15/10/2017.
 */

public class StepsAdapter extends BaseQuickAdapter<Step,BaseViewHolder> {
	private Context mContext;

	public StepsAdapter(@Nullable List<Step> data, Context context) {
		super(R.layout.step_detail_list_item, data);
		this.mContext = context;
	}

	@Override
	protected void convert(BaseViewHolder helper, Step item) {

		Glide.with(mContext)
				.load(item.getThumbnailURL())
				.apply(RequestOptions.centerCropTransform())
				.into((ImageView) helper.getView(R.id.step_thumbnail_iv));

		helper.setText(R.id.step_shortdescription_tv, item.getShortDescription());
		helper.setText(R.id.step_number_tv, item.getId() + "");
	}
}