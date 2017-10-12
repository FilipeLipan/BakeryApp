package com.github.filipelipan.bakeryapp.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.filipelipan.bakeryapp.R;
import com.github.filipelipan.bakeryapp.util.rx.RxHttpError;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import java.net.HttpURLConnection;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lispa on 12/10/2017.
 */


public abstract class AppFragment<V extends AppView, P extends MvpPresenter<V>> extends MvpFragment<V, P> implements IErrorHandlerView, AppView {

    private IAppActivityListener iAppActivityListener;
    private Unbinder unbinder;

    @Override
    public boolean hasInternetConnection() {
        return getAppActivityListener().hasInternetConnection();
    }

    public void showLoading(boolean cancelable) {
        getAppActivityListener().showLoading(cancelable);
    }

    public void showLoading(boolean cancelable, LoadingView.CancelableTaskListener listener) {
        getAppActivityListener().showLoading(cancelable, listener);
    }

    public void dismissLoading() {
        getAppActivityListener().dismissLoading();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iAppActivityListener = (IAppActivityListener) context;
    }

    @Override
    public void error(RxHttpError error) {
        switch (error.errorCode) {
            case RxHttpError.SOCKETTIMEOUT_CODE:
            case RxHttpError.UNKNOWNHOST_CODE:
//                setMessageContent(getString(R.string.err_unknown_host));
                break;
            case RxHttpError.NO_CONNECTIVITY_CODE:
//                setMessageContent(getString(R.string.err_connectivity));
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                logout();
//                CustomApplication.getInstance().doLogout( getContext() );
                break;
            case 422:
//                setMessageContent(true, error.detail, getSectionIcPlaceholder());
                break;
            default:
                setMessageContent(getString(R.string.err_default));
                break;
        }
    }

    public void logout() {
        getAppActivityListener().logout();
        getActivity().finish();
    }


    @Override
    public void error(Throwable e) {
        e.printStackTrace();
        setMessageContent(getString(R.string.err_default));
    }

    protected void setMessageContent(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    /**
     * Tag para identificacao do Fragmento na pilha do FragmentManager
     *
     * @return Tag que identifica o fragmento
     */
    public abstract String getFragmentTag();

    public abstract int getFragmentLayout();

    protected IAppActivityListener getAppActivityListener() {
        return iAppActivityListener;
    }
}