package com.coahr.thoughtrui.mvp.Base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import androidx.annotation.Keep;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.multidex.MultiDex;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/23
 * 描述：
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    private Context mcontext;
    private String appVersion;

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(BaseApplication.class)
    static class RealApplicationStub {

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mcontext=base;
//         如果需要使用MultiDex，需要在此处调用。
         MultiDex.install(base);
        initSophix();
    }
    private void initSophix() {
        appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;

        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25584439-1","71ac1e1bef6bc6bb248d895e06b63cbc","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGtLRYWTyuxRBJI8RSYjOPoLuchLXEL2b0yzzJAZiMU2qJBPQRUe/yCVL9sdnD54V69CKupR7KMl2paVU45Y4yIe6Sq53Y4AJWXjBgEFSgMSl4JzNIsK8xzRrH8tMioCP0i0oZMc5G5zJFAOl0FuhJLknzFCSu2zXUDTPcyCIXxa1dQ2GuDiA63fnAUCl1GgKTygJJrf8fDNKL/+Ac1RwiL1FOgAtLj9GnjyrGJtzowsszQCe4WvIeZbEt7G6WT1EZpwK01d2vdJdFZG72GoZaiCTTHR2UJgsbxlbNW7wHpwHvmIZdziugRBHaQk/8KVXtT4U3NMTy/QgAr06vEwtXAgMBAAECggEABGBAb5JOj1rlgYrA6PA5vpO5JIeHJB1qGc+0ZM9BdmWLU3Iuv5VK1zEIWid57IxL4MenjMbebjEeq885LoT+jpBdoLQkh5QXX1jl92jwZtfSAg4780OSHWsNUKuJwlkzcdFIkfL1QYZnMqip7NSFrULsshHYczm50O6w3Z4+xUjhjtSvi2HJeSos6Pb+xCDa8P6edf+aLpG45YS+vH8mHQTZ6h1logYGucNsWgsQDH7DyRRswKb7F/ikzGHxAtpi4xl3L1G8Zyx35eEHxJSNWGHNxBbgOx7msFkOOyNGQI0HStq/y4oZeD/AZ6soCXD7ugkUXqK6nEbFGq3PkXjB8QKBgQDMeRz1mRFRT/1Q6NJXS6GEUPLeHDyAUOKekXEJaxB1rN+lDNb5uNNFYtNCJyVSuxDJfAkKpoyq/qHRwDz5olQJfh7+qDAzcNdB8yY3sZhiWo49EL4zUCgKQAF1SfDw9ikxI/ZhmIGkXIDwV+zmFqLSNNoYh9am3Xd5lUCR1xPGXwKBgQCopsvVSZ1aC78aNxjZHw4RlA7hP1olcXfTJzYwIIbkPl0l8p9b9Czi3qD1yrmfZQe1BL9e1XmoC/TXAhkarKEB7X7X00zWgV2YYDMyJNh8JZg/ynheTTwxSXZpfIChfB1HQAZZZykOIwd4ortqi9Igwdxs1uqIf+RiHaUO78QuCQKBgDiX0O8dBZEG3ar2NbmZokO4D/BvykMNoBuZT7r2miCnz2KkUh/eCwOqXaRypz7lrbjGjs4W2No/DdS7K2VAi5fxA20iaezi74E+ZjaF/hJC1BmMt8gGCH8FxiGLbJeU2kPSm0/Z4Q/31mwvOc9ZNomNvUuK1Vtr8rSBHdT/vWZrAoGBAKfhKSQOXFDnQnrA/74ZuLJs1IcJvh1pnuKUtM8hgcUwAx8kLdel7wyCmm0xOKdfNVXO/QRUsf5CsJ1aXEz+LGOz2sDSeDlKmzV0BWJT5R9neO9/B2Svs0xImVOV+KFG4AotxGQ4WVjWK7i9HJyJxEiRUW8SXHI2BMxaQBTnPTypAoGANDis+n1PkJojRde0o39qh+oZz24ETM2CdakZqDWh3AGs0nlp9iEcq5hKM328sKFBofOlh4kEkuvp1HSDk6+1m0cb9PZknKZ8QNQZfC9ncLUx0stHEVXIhEkbf6YCHUByuWfEbl160Rs9r5OIr/uur7DcMkkbWHDSe2/E0Wt63Cc=")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.d(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            Log.d(TAG, "发送广播，重启");
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(mcontext);
                            PreferenceUtils.setPrefBoolean(mcontext, Constants.AliYunHot_key,true);
                            PreferenceUtils.setPrefString(mcontext, Constants.path_key,info);
                            PreferenceUtils.setPrefString(mcontext, Constants.app_version_key, appVersion);
                            PreferenceUtils.setPrefString(mcontext, Constants.pathVersion_key,String.valueOf(handlePatchVersion));
                            Intent intent = new Intent();
                            intent.setAction("hotAliyun");
                            localBroadcastManager.sendBroadcast(intent);
                        } else {
                            Log.d(TAG, "暂无阿里云补丁");
                        }
                    }
                }).initialize();
    }
}
