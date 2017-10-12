package com.github.filipelipan.bakeryapp.common;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by lispa on 12/10/2017.
 */

public interface AppView extends MvpView, IErrorHandlerView{
    boolean hasInternetConnection();
    void showLoading(boolean cancelable);
    void showLoading(boolean cancelable, LoadingView.CancelableTaskListener listener);
    void dismissLoading();
}
