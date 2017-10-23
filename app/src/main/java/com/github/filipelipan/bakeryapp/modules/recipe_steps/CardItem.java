package com.github.filipelipan.bakeryapp.modules.recipe_steps;

/**
 * Created by lispa on 22/10/2017.
 */

public class CardItem {

	private int mTextResource;
	private int mTitleResource;

	public CardItem(int title, int text) {
		mTitleResource = title;
		mTextResource = text;
	}

	public int getText() {
		return mTextResource;
	}

	public int getTitle() {
		return mTitleResource;
	}
}