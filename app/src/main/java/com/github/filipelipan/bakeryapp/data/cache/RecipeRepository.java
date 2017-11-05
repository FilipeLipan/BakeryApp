package com.github.filipelipan.bakeryapp.data.cache;

import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.data.ws.RestApi;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;

/**
 * Created by lispa on 12/10/2017.
 */

public class RecipeRepository extends BaseRepository {
	public RecipeRepository(File cacheDir, RestApi restApi) {
		super(cacheDir, restApi);
	}

	public Observable<ArrayList<Recipe>> get(){
		return providers.getRecipes(
				restApi.getRecipes(),
				new DynamicKey("recipe_primary_key"),
				new EvictProvider(false)
		);
	}
}
