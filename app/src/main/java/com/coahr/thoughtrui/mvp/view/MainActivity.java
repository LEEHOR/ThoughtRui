package com.coahr.thoughtrui.mvp.view;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ActivityManagerUtils;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.Utils.UpdateVersion.CheckVersion;
import com.coahr.thoughtrui.Utils.UpdateVersion.InstallApk;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.presenter.MainActivityP;
import com.coahr.thoughtrui.mvp.view.home.MainInfoFragment;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.MyBottomNavigation.MyBottomNavigation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDialogFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainActivityC.Presenter> implements MainActivityC.View {

    @Inject
    MainActivityP p;
    @BindView(R.id.myBottom)
    MyBottomNavigation myBottomNavigation;
    //正在展示的fragment的position
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[4];
    private long exitTime = 0;
    private static final long INTERVAL_TIME = 2000;
    private String sessionId;
    private int page = 0; //当前显示页面
    private static int TIMES = 1000 * 20 * 60;  //发送定位时间间隔
    private static int SEND_MESSAGE = 1;
    private GaodeMapLocationHelper gaodeMapLocationHelper_s;
    private Handler mHandker = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private Login_DialogFragment login_dialogFragment;
    private CheckVersion checkVersion;

    @Override
    public MainActivityC.Presenter getPresenter() {
        return p;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mFragments[0] = findFragment(MainInfoFragment.class);
            mFragments[1] = findFragment(UploadFragment.class);
            mFragments[2] = findFragment(ReviewedFragment.class);
            mFragments[3] = findFragment(MyFragment.class);
        } else {
            mFragments[0] = MainInfoFragment.newInstance();
            mFragments[1] = UploadFragment.newInstance();
            mFragments[2] = ReviewedFragment.newInstance();
            mFragments[3] = MyFragment.newInstance();
        }
        super.onCreate(savedInstanceState);
       /* AlarmTimerUtil  alarmTimerUtil = AlarmTimerUtil.getInstance(BaseApplication.mContext);
        alarmTimerUtil.createGetUpAlarmManager(BaseApplication.mContext,"TIMER_ACTION",10);
        alarmTimerUtil.getUpAlarmManagerStartWork();*/
     /*   Intent intent = new Intent(this, LocalService.class);
        Intent intents = new Intent(this, RomoteService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
            startForegroundService(intents);
        } else {  
            startService(intent);
            startService(intents);
        }*/
        EventBus.getDefault().register(this);
        checkVersion();
    }

    private void checkVersion() {
        //InstallApk.openAPKFile(this,"/storage/emulated/0/com.thoughtRui.coahr/downLoadApk/RBJ掌上检核v1.0.3.apk");
        checkVersion =new CheckVersion(this);
        checkVersion.check();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandker != null) {
            mHandker.removeCallbacksAndMessages(null);
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * Evenbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EvenBus(EvenBus_LoginSuccess evenBus_loginSuccess) {
        if (evenBus_loginSuccess.getLoginType() == 100) {
            showFragment(0);
        }
    }

    @Override
    public int binLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        getLocationPermission();
        loadMultipleRootFragment(R.id.Root_Fragment, 0, mFragments);
        showHideFragment(mFragments[0], mFragments[bottomNavigationPreposition]);
        myBottomNavigation.beanSelect(0);
        bottomNavigationPreposition = 0;
        if (!haslogin()) {
            loginDialog();
        }
       // CrashReport.testJavaCrash();
        myBottomNavigation.setOnTabPositionListener(new MyBottomNavigation.OnTabPositionListener() {
            @Override
            public void onPositionTab(int position) {
                showFragment(position);
            }
        });
    }

    @Override
    public void initData() {
        p.getLocation(1);
    }

    private void showFragment(int position) {
        page = position;
        if (!haslogin()) {
            loginDialog();
        } else {
            showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
            myBottomNavigation.beanSelect(position);
            bottomNavigationPreposition = position;
        }

    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > INTERVAL_TIME) {
            ToastUtils.showLong(getResources().getString(R.string.carsuper_exit));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            ActivityManagerUtils.getInstance().appExit();
        }
    }

    /**
     * 动态获取定位权限
     */
    @SuppressLint("MissingPermission")
    private void getLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(MainActivity.this, new OnRequestPermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void PermissionSuccess(List<String> permissions) {

                }

                @Override
                public void PermissionFail(List<String> permissions) {
                    new MaterialDialog.Builder(BaseApplication.mContext)
                            .title(getResources().getString(R.string.message_permission_tittle))
                            .content(getResources().getString(R.string.message_permission_content))
                            .negativeText(getResources().getString(R.string.cancel))
                            .positiveText(getResources().getString(R.string.resume))
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Uri packageURI = Uri.parse("package:" + "com.coahr.thoughtrui");
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                    startActivity(intent);
                                }
                            })
                            .build().show();

                }

                @Override
                public void PermissionHave() {

                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);

        } else {

        }
    }

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        if (login_dialogFragment == null) {
            login_dialogFragment = Login_DialogFragment.newInstance(Constants.MainActivityCode);

            login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
                @Override
                public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                    dialogFragment.dismiss();
                    login_dialogFragment = null;
                    showFragment(0);
                }
            });
            login_dialogFragment.show(getSupportFragmentManager(), TAG);
        }
    }


    @Override
    public void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper) {
        this.gaodeMapLocationHelper_s = gaodeMapLocationHelper;
        Constants.Longitude = location.getLongitude();
        Constants.Latitude = location.getLatitude();
        gaodeMapLocationHelper.stopLocation();
        mHandker.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendRTSL(location.getLongitude(), location.getLatitude());
                p.getLocation(1);
            }
        }, TIMES);
    }

    @Override
    public void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper) {
        this.gaodeMapLocationHelper_s = gaodeMapLocationHelper;
        gaodeMapLocationHelper_s.stopLocation();
        p.getLocation(1);
    }

    @Override
    public void sendRtslSuccess(String success, int result) {

    }

    @Override
    public void sendRtslFail(String fail, int result) {
        if (result == -1) {
            loginDialog();
        }
    }

    private void sendRTSL(double lon, double lat) {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("token", Constants.devicestoken);
        map.put("longitude", String.valueOf(lon));
        map.put("latitude", String.valueOf(lat));
        p.sendRtsl(map);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int page = intent.getIntExtra("page", 0);
        showFragment(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gaodeMapLocationHelper_s != null) {
            gaodeMapLocationHelper_s.stopLocation();
            p.getLocation(1);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gaodeMapLocationHelper_s != null) {
            gaodeMapLocationHelper_s.stopLocation();
            mHandker.removeCallbacksAndMessages(null);
        }
    }

}
