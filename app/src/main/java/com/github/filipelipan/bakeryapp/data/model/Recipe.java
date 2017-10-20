package com.github.filipelipan.bakeryapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lispa on 12/10/2017.
 */

public class Recipe implements Parcelable {
	private int id;
	private String name;
	private int servings;
	private String image;
	private ArrayList<Step> steps;
	private ArrayList<Ingredient> ingredients;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeInt(this.servings);
		dest.writeString(this.image);
		dest.writeList(this.steps);
		dest.writeTypedList(this.ingredients);
	}

	public Recipe() {
	}

	protected Recipe(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.servings = in.readInt();
		this.image = in.readString();
		this.steps = new ArrayList<Step>();
		in.readList(this.steps, Step.class.getClassLoader());
		this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
	}

	public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel source) {
			return new Recipe(source);
		}

		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};
}
