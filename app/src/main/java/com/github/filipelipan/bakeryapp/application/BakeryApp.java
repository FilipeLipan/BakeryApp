package com.github.filipelipan.bakeryapp.application;

import android.support.multidex.MultiDexApplication;

import com.github.filipelipan.bakeryapp.data.cache.RecipeRepository;
import com.github.filipelipan.bakeryapp.data.ws.RestApi;
import com.github.filipelipan.bakeryapp.data.ws.RestClient;

/**
 * Created by lispa on 12/10/2017.
 */

public class BakeryApp extends MultiDexApplication {
	private static BakeryApp sInstance;
	private RestApi mRestApi;
	private RecipeRepository mRecipeRepository;

	public static BakeryApp getsInstance(){
		return sInstance;
	};

	@Override
	public void onCreate() {
		super.onCreate();

		sInstance = this;

		mRestApi = new RestClient().getApi();

		mRecipeRepository = new RecipeRepository(getFilesDir(), getRestApi());
	}

	public RestApi getRestApi() {
		return mRestApi;
	}

	public RecipeRepository getRecipeRepository() {
		return mRecipeRepository;
	}
}
