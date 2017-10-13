package com.github.filipelipan.bakeryapp.data.model;

/**
 * Created by lispa on 12/10/2017.
 */

public class Ingredient {
	private float quantity;
	private String measure;
	private String ingredient;


	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
}
