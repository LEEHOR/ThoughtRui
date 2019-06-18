package com.coahr.thoughtrui.Utils.UpdateVersion;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;

/**
 * 更新的广播接收器
 */
public class DownloadReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            String url = PreferenceUtils.getPrefString(context, Constants.URL, "");
            if (!TextUtils.isEmpty(url)) {
               // KLog.d("版本","安装了/"+ url);
                InstallApk.openAPKFile(context, url);
            }
        }
    }
}
