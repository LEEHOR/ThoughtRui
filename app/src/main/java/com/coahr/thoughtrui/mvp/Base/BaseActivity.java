package com.coahr.thoughtrui.mvp.Base;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.ActivityManager;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.NetWorkReceiver;
import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;
import com.taobao.sophix.SophixManager;

import org.litepal.LitePal;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:44
 */
public abstract class BaseActivity<P extends BaseContract.Presenter> extends SupportActivity {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();


    private Dialog dialog;

    private Unbinder unbinder;
    private NetWorkReceiver netWorkReceiver;

    public abstract P getPresenter();

    public abstract int binLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       AndroidInjection.inject(this);  //一处声明，处处依赖注入
        super.onCreate(savedInstanceState);
        setContentView(binLayout());
        KeyBoardUtils.UpdateUI(getWindow().getDecorView().getRootView(), this);
        //手机顶部状态栏颜色适配
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        unbinder = ButterKnife.bind(this);
        LitePal.getDatabase();
        ActivityManager.getInstance().addActivity(this);
        initView();
        initData();
        /**
         * 网络广播
         */
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter_net = new IntentFilter();
        filter_net.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter_net.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter_net.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(netWorkReceiver, filter_net);
        netWorkReceiver.setNetStatusListener(new NetWorkReceiver.INetStatusListener() {
            @Override
            public void getNetState(String netWorkType, boolean isConnect, NetworkInfo.DetailedState detailedState) {
                Constants.isNetWorkConnect=isConnect;
                Constants.NetWorkType=netWorkType;
                KLog.d("网络",isConnect,netWorkType);
            }
        });
    }
    public void showLoading() {

    }
    public void dismissLoading() {

    }
    public void showError(@Nullable Throwable e) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManager.getInstance().removeActivity(this);
        ImmersionBar.with(this).destroy();
        if (netWorkReceiver != null) {
            this.unregisterReceiver(netWorkReceiver);
        }
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
    }

    public boolean haslogin() {
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.sessionId_key)) {
            if (Constants.sessionId.equals("")) {
                Constants.sessionId = PreferenceUtils.getPrefString(this, Constants.sessionId_key, "");
                Constants.user_name = PreferenceUtils.getPrefString(this, Constants.user_key, "");
            }
            return true;
        }
        return false;
    }
    /**
     * @param title
     * @param Content
     */
    public void Dialog(String title, String Content) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(Content)
                .negativeText("下次再说")
                .positiveText("立即更新")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        PreferenceUtils.remove(BaseApplication.mContext,  Constants.AliYunHot_key);
                        PreferenceUtils.remove(BaseApplication.mContext,Constants.pathVersion_key);
                        PreferenceUtils.remove(BaseApplication.mContext,Constants.path_key);
                        dialog.dismiss();
                        SophixManager.getInstance().killProcessSafely();

                    }
                }).build().show();
    }
}
