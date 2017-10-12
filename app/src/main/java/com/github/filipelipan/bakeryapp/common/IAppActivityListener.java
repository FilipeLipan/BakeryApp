package com.github.filipelipan.bakeryapp.common;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lispa on 12/10/2017.
 */

public interface IAppActivityListener {

    void bindView();
    void setupLoadingFragment(LoadingView loadingView);
    void setContainer(FrameLayout container);
    /**
     * Titulo para action bar
     * @param title
     */
    void setTitle(String title);
    /**
     * Titulo para action bar
     * @param title
     */
//    void setCenterTitle(String title);
    /**
     * Obtem a toolbar
     * @return
     */
//    Toolbar getToolbar();
    void setToolbar(Toolbar toolbar);
    /**
     * Troca o fragmento que herde AppFragment
     * @param fragment
     *
     * @see AppFragment
     *
     */
    void replaceFragment(AppFragment fragment);
    /**
     * Troca o fragmento que herde AppFragment e adiciona na Pilha de Voltar do FragmentManager
     * @param fragment
     *
     * @see AppFragment
     *
     */
    void replaceAndBackStackFragment(AppFragment fragment);


    boolean hasInternetConnection();


    void showLoading(boolean cancelable);
    void showLoading(boolean cancelable, LoadingView.CancelableTaskListener listener);
    void dismissLoading();

    View inflateView(int layout, View viewGroup);

    void logout();
}
