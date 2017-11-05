package com.github.filipelipan.bakeryapp.modules.recipe;

import com.github.filipelipan.bakeryapp.common.AppPresenter;
import com.github.filipelipan.bakeryapp.data.cache.RecipeRepository;
import com.github.filipelipan.bakeryapp.data.model.Recipe;
import com.github.filipelipan.bakeryapp.data.ws.RestApi;
import com.github.filipelipan.bakeryapp.util.rx.IErrorHandlerHelper;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lispa on 12/10/2017.
 */

public class RecipeListPresenter extends AppPresenter<IRecipeListView> {

	private final RecipeRepository mRecipeRepository;

	public RecipeListPresenter(RecipeRepository recipeRepository) {
		this.mRecipeRepository = recipeRepository;
	}

	public void getRecipes(){

		if(isViewAttached()) {

			mRecipeRepository.get()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new DisposableObserver<ArrayList<Recipe>>() {
						@Override
						public void onNext(ArrayList<Recipe> recipes) {
							if(isViewAttached()){
								getView().loadRecipes(recipes);
							}
						}

						@Override
						public void onError(Throwable e) {
							if(isViewAttached()){
								IErrorHandlerHelper.defaultErrorResolver(RecipeListPresenter.this.getView(), e);
							}
						}

						@Override
						public void onComplete() {

						}
					});
		}
	}
}
