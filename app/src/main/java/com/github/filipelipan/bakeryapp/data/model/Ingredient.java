package com.github.filipelipan.bakeryapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lispa on 12/10/2017.
 */

public class Ingredient implements Parcelable {
	private float quantity;
	private String measure;
	private String ingredient;

	public float getQuantity() {
		return quantity;
	}

	public Ingredient setQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public String getMeasure() {
		return measure;
	}

	public Ingredient setMeasure(String measure) {
		this.measure = measure;
		return this;
	}

	public String getIngredient() {
		return ingredient;
	}

	public Ingredient setIngredient(String ingredient) {
		this.ingredient = ingredient;
		return this;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(this.quantity);
		dest.writeString(this.measure);
		dest.writeString(this.ingredient);
	}

	public Ingredient() {
	}

	protected Ingredient(Parcel in) {
		this.quantity = in.readFloat();
		this.measure = in.readString();
		this.ingredient = in.readString();
	}

	public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
		@Override
		public Ingredient createFromParcel(Parcel source) {
			return new Ingredient(source);
		}

		@Override
		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};
}
