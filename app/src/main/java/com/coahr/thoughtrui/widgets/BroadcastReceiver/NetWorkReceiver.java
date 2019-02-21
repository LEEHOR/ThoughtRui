package com.coahr.thoughtrui.widgets.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.socks.library.KLog;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/14
 * 描述：网络监听广播
 */
public class NetWorkReceiver extends BroadcastReceiver {

    private INetStatusListener netStatusListener;
    private ConnectivityManager cm;
    private NetworkInfo activeNetworkInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        String netWorkSubtypeName = isNetWorkSubtypeName(activeNetworkInfo);
        boolean netWorkConnected = isNetWorkConnected(activeNetworkInfo);
        NetworkInfo.DetailedState detailedState = getDetailedState(activeNetworkInfo);
        if (netStatusListener != null) {
            netStatusListener.getNetState(netWorkSubtypeName,netWorkConnected,detailedState);
        }
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public boolean isNetWorkConnected(NetworkInfo activeNetworkInfo) {
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断当前网络类型
     *"WIFI" or "MOBILE"
     */
    public String isNetWorkSubtypeName(NetworkInfo activeNetworkInfo) {
        if (activeNetworkInfo != null) {
            String subtypeName = activeNetworkInfo.getTypeName();
            return  subtypeName;
        }
        return null;
    }

    /**
     * 获取网络连接的详细状态
     * @param activeNetworkInfo
     */
    public NetworkInfo.DetailedState getDetailedState(NetworkInfo activeNetworkInfo){
        if (activeNetworkInfo != null) {
            NetworkInfo.DetailedState detailedState = activeNetworkInfo.getDetailedState();
            return detailedState;
        }
        return null;
    }

    public interface INetStatusListener{
         void getNetState(String netWorkType,boolean isConnect,NetworkInfo.DetailedState detailedState);
    }

    public void setNetStatusListener(INetStatusListener netStatusListener) {
        this.netStatusListener = netStatusListener;
    }
}
