package com.github.filipelipan.bakeryapp.common;

import android.support.v4.app.FragmentManager;

import java.io.Serializable;

/**
 * Created by lispa on 12/10/2017.
 */

public interface LoadingView {

    void setListener(CancelableTaskListener listener);

    void cancelableOnBackPressed(boolean cancelable);

    String getViewTag();

    void show(FragmentManager fragmentManager, String tag);
    void dismiss();

    interface CancelableTaskListener extends Serializable {
        void onCancelTask();
    }
}
