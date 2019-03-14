package com.coahr.thoughtrui.widgets.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/24
 * 描述：监听阿里云补丁更新广播
 */
public class AliyunHotReceiver extends BroadcastReceiver {
  private hotListener hotListener;
    @Override
    public void onReceive(Context context, Intent intent) {

    }
    public interface hotListener{
        void getPathDetail(String path_info,String path_version,String app_version);
    }

    public void setHotListener(AliyunHotReceiver.hotListener hotListener) {
        this.hotListener = hotListener;
    }
}
