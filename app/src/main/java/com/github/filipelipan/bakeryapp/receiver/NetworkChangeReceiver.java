package com.github.filipelipan.bakeryapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.github.filipelipan.bakeryapp.util.ConnectionUtils;

/**
 * Created by lispa on 12/10/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String NOTIFY_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String EXTRA_IS_CONNECTED = "EXTRA_IS_CONNECTED";
    private boolean mOnline;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent localIntent = new Intent(NOTIFY_NETWORK_CHANGE);
        mOnline = ConnectionUtils.isOnline(context);
        localIntent.putExtra(EXTRA_IS_CONNECTED, mOnline);
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }

    public boolean isOnline(){
        return mOnline;
    }


    public void forceUpdateState(Context context) {
        mOnline = ConnectionUtils.isOnline(context);
    }
}