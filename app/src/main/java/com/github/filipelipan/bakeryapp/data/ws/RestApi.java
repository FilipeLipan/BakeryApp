package com.github.filipelipan.bakeryapp.data.ws;

import com.github.filipelipan.bakeryapp.data.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by lispa on 12/10/2017.
 */

public interface RestApi {


	@GET("/topher/2017/May/59121517_baking/baking.json")
	Observable<ArrayList<Recipe>> getRecipes();
}
