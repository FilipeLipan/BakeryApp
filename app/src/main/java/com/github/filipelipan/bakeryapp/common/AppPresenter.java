package com.github.filipelipan.bakeryapp.common;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lispa on 12/10/2017.
 */

public class AppPresenter<V extends MvpView> extends MvpBasePresenter<V> {
    protected final CompositeDisposable disposables = new CompositeDisposable();

    public void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }

    @Override
    public void detachView(boolean retainInstance) {
        disposables.clear();
    }
}
