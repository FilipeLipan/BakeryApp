package com.github.filipelipan.bakeryapp.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.github.filipelipan.bakeryapp.modules.recipe.RecipeListFragment;
import com.github.filipelipan.bakeryapp.receiver.NetworkChangeReceiver;
import com.github.filipelipan.bakeryapp.util.rx.RxHttpError;

import java.net.HttpURLConnection;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lispa on 12/10/2017.
 */
public abstract class AppActivity extends AppCompatActivity implements IAppActivityListener, IErrorHandlerView, AppView{

    FrameLayout container;

    private NetworkChangeReceiver networkReceiver;
    private List<InternetConnectionListener> internetListenerList;


    private LoadingView loadingView;

    public void bindView() {
        ButterKnife.bind(this);
    }

    public void showLoading(boolean cancelable) {
        if (loadingView != null) {
            loadingView.cancelableOnBackPressed(cancelable);
            loadingView.show(getSupportFragmentManager(), loadingView.getViewTag());
        }
    }

    public void showLoading(boolean cancelable, LoadingView.CancelableTaskListener listener) {
        if (loadingView != null && listener != null) {
            loadingView.setListener(listener);
            showLoading(cancelable);
        }
    }

    public void dismissLoading() {
        if (loadingView != null) {
            loadingView.dismiss();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBroadcastReceiver(true);

    }

    @Override
    public void setupLoadingFragment(LoadingView loadingView) {
        this.loadingView = loadingView;
    }

    @Override
    public void setContainer(FrameLayout container) {
        this.container = container;
    }

    @Override
    public void replaceFragment(AppFragment fragment) {
        FragmentTransaction ft = getFragmentTransaction();

        ft.replace(container.getId(), fragment, fragment.getFragmentTag());
        ft.commit();
    }

    @Override
    public void replaceAndBackStackFragment(AppFragment fragment) {
        FragmentTransaction ft = getFragmentTransaction();

        ft.replace(container.getId(), fragment, fragment.getFragmentTag());
        ft.addToBackStack(fragment.getFragmentTag());
        ft.commit();
    }

    private FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    public void logout() {

    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean hasInternetConnection() {
        return networkReceiver.isOnline();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupBroadcastReceiver(true);
    }

    @Override
    protected void onPause() {
        setupBroadcastReceiver(false);

        super.onPause();
    }

    @Override
    public void error(RxHttpError error) {
        //TODO handle show errors
        switch (error.errorCode) {
            case RxHttpError.SOCKETTIMEOUT_CODE:
            case RxHttpError.UNKNOWNHOST_CODE:
//                setMessageContent(true, R.string.err_unknownhost, getSectionIcPlaceholder());
                break;
            case RxHttpError.NO_CONNECTIVITY_CODE:
//                setMessageContent(true, R.string.err_connectivity, getSectionIcPlaceholder());
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                logout();
//                CustomApplication.getInstance().doLogout( getContext() );
                break;
            case 422:
//                setMessageContent(true, error.detail, getSectionIcPlaceholder());
                break;
            default:
//                setMessageContent(true, R.string.err_default, getSectionIcPlaceholder());
                break;
        }
    }

    @Override
    public View inflateView(int resource, View viewGroup) {
        View view = getLayoutInflater().inflate(resource, (ViewGroup) viewGroup.getParent(), false);
        return view;
    }

    @Override
    public void error(Throwable e) {
        e.printStackTrace();
//        setMessageContent(true, R.string.err_default, getSectionIcPlaceholder());
    }

    public void setupBroadcastReceiver(boolean on) {
        if (networkReceiver == null)
            networkReceiver = new NetworkChangeReceiver();

        if (on) {
            registerReceiver(networkReceiver, new IntentFilter(NetworkChangeReceiver.NOTIFY_NETWORK_CHANGE));

            IntentFilter customFilter = new IntentFilter(NetworkChangeReceiver.NOTIFY_NETWORK_CHANGE);
            LocalBroadcastManager.getInstance(this).registerReceiver(mLocalReceiver, customFilter);

            networkReceiver.forceUpdateState(this);
//            Intent intent = new Intent(".core.receiver.NetworkChangeReceiver");
//            sendBroadcast(intent);
        } else {
            unregisterReceiver(networkReceiver);
        }
    }

    private BroadcastReceiver mLocalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean internetConnection = intent.getBooleanExtra(NetworkChangeReceiver.EXTRA_IS_CONNECTED, false);

            if (internetListenerList != null && !internetListenerList.isEmpty()) {
                for (InternetConnectionListener listener : internetListenerList) {
                    listener.onInternetConnectionChange(internetConnection);
                }

            }
        }
    };
}
