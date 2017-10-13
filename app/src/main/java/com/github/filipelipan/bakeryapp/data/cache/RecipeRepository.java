package com.github.filipelipan.bakeryapp.data.cache;

import com.github.filipelipan.bakeryapp.data.ws.RestApi;

import java.io.File;

/**
 * Created by lispa on 12/10/2017.
 */

public class RecipeRepository extends BaseRepository {
	RecipeRepository(File cacheDir, RestApi restApi) {
		super(cacheDir, restApi);
	}
}
