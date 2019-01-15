package com.coahr.thoughtrui.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by Leehor
 * on 2018/11/14
 * on 8:57
 */
public class NetWorkAvailable {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
//如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (activeNetworkInfo !=null){
                return activeNetworkInfo.isConnected();
            }
        }
//        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
//        String subtypeName = activeNetworkInfo.getSubtypeName();
        return false;
    }

    // 判断网络是否可用
    public boolean isNetWorkConnected(Context context) {
        if (context!=null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context!=null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 判断MOBILE网络是否可用
    public boolean isMobileConnected(Context context) {
        if (context!=null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 获得当前网络连接的类型信息
    public static int getConnectedType(Context context) {
        if (context!=null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo!=null && mNetworkInfo.isAvailable()){
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
