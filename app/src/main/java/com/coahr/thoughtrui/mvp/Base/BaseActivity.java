package com.coahr.thoughtrui.mvp.Base;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.ActivityManager;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.NetWorkReceiver;
import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;

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

    private Dialog wifiConnectedDialog;

    private PopupWindow popupWindow;

    public abstract P getPresenter();

    public abstract int binLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);  //一处声明，处处依赖注
        super.onCreate(savedInstanceState);
        //手机顶部状态栏颜色适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                    .init();
        }
        KeyBoardUtils.UpdateUI(getWindow().getDecorView().getRootView(), this);
        setContentView(binLayout());
        unbinder = ButterKnife.bind(this);
        LitePal.getDatabase();
        ActivityManager.getInstance().addActivity(this);
        initView();
        initData();

        //网络广播
        initNetWorkReceiver();

        // TimeTaskReceiver timeTaskReceiver = new TimeTaskReceiver();

    }

    /**
     * 注册网络广播
     */
    private void initNetWorkReceiver() {
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter_net = new IntentFilter();
        filter_net.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter_net.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter_net.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(netWorkReceiver, filter_net);
        netWorkReceiver.setNetStatusListener(new NetWorkReceiver.INetStatusListener() {
            @Override
            public void getNetState(String netWorkType, boolean isConnect, NetworkInfo.DetailedState detailedState) {
                KLog.d("测试代码", "isConnect == " + isConnect, " -- netWorkType == " + netWorkType);
                Constants.isNetWorkConnect = isConnect;
                Constants.NetWorkType = netWorkType;

                //链接WIFI
                JudgeWifiConnectedUpload(netWorkType);
            }
        });
    }

    /**
     * 判断是否设置wifi连接后自动上传
     */
    private void JudgeWifiConnectedUpload(String netWorkType) {
        if (PreferenceUtils.getPrefBoolean(BaseActivity.this, Constants.WIFI_CONNECTED_UPLOAD_KEY, false)) {
            if (Constants.isNetWorkConnect && "WIFI".equals(netWorkType)) {
                if (!Constants.isWifiDialogAlreadyShow) {
                    Constants.isWifiDialogAlreadyShow = true;

                    showWifiConnectedDialog();
                }
            }
        }
    }

    /**
     * Wifi连接后，提示上传Dialog
     */
    private void showWifiConnectedDialog() {
        //初始化Dialog布局
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_wifi_connected_upload, null);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvUpload = dialogView.findViewById(R.id.tv_upload);
        tvCancel.setOnClickListener(clickListener);
        tvUpload.setOnClickListener(clickListener);

        wifiConnectedDialog = new Dialog(this);
        wifiConnectedDialog.setContentView(dialogView);
        wifiConnectedDialog.setCancelable(false);
        wifiConnectedDialog.setCanceledOnTouchOutside(false);
        wifiConnectedDialog.show();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    break;
                case R.id.tv_upload:
                    if (BaseActivity.this instanceof MainActivity) {
                        //跳转到上传页面
                        ((MainActivity) BaseActivity.this).showFragment(1);
                    }
                    break;
            }
            wifiConnectedDialog.dismiss();
        }
    };

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ImmersionBar.with(this).destroy();
        }
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
}
