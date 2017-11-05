package com.github.filipelipan.bakeryapp.data.cache;

import com.github.filipelipan.bakeryapp.data.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * Created by lispa on 12/10/2017.
 */

public interface Providers {

	Observable<ArrayList<Recipe>> getRecipes(Observable<ArrayList<Recipe>> apiRequest, DynamicKey key, EvictProvider evictProvider);
}
