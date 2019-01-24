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

        KLog.d("SophixStubApplication","接受广播1");
        if (getHotUpdate()) {
            String path_key = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.path_key, "");
            String pathVersion_key = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.pathVersion_key, "");
            String app_version_key = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.app_version_key, "");
            KLog.d("测试补丁1");
            if (hotListener != null) {
                hotListener.getPathDetail(path_key,pathVersion_key,app_version_key);
                KLog.d("SophixStubApplication","接受广播2");
            }
        }

    }
    /**
     * 阿里云热更新检测
     */
    public boolean getHotUpdate() {
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.AliYunHot_key)) {
            boolean prefBoolean = PreferenceUtils.getPrefBoolean(BaseApplication.mContext, Constants.AliYunHot_key, false);
            return prefBoolean;
        }
        return false;
    }
    public interface hotListener{
        void getPathDetail(String path_info,String path_version,String app_version);
    }

    public void setHotListener(AliyunHotReceiver.hotListener hotListener) {
        this.hotListener = hotListener;
    }
}
