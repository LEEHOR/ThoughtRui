package com.coahr.thoughtrui.mvp.view;


import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.Toast;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ActivityManagerUtils;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.presenter.MainActivityP;
import com.coahr.thoughtrui.mvp.view.TimeService.AlarmTimerUtil;
import com.coahr.thoughtrui.mvp.view.home.MainInfoFragment;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.AliyunHotReceiver;
import com.coahr.thoughtrui.widgets.MyBottomNavigation.MyBottomNavigation;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import javax.inject.Inject;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
    private boolean prefBoolean;
    private AliyunHotReceiver aliyunHotReceiver;
    private LocalBroadcastManager manager;
    private AlarmTimerUtil alarmTimerUtil;

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
        alarmTimerUtil = AlarmTimerUtil.getInstance(BaseApplication.mContext);
        alarmTimerUtil.createGetUpAlarmManager(this,"TIMER_ACTION",10);
        alarmTimerUtil.getUpAlarmManagerStartWork();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if (aliyunHotReceiver != null) {
            manager.unregisterReceiver(aliyunHotReceiver);
        }*/

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * Evenbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EvenBus(EvenBus_LoginSuccess evenBus_loginSuccess) {
        if (evenBus_loginSuccess.getLoginType()==100){
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
        myBottomNavigation.setOnTabPositionListener(new MyBottomNavigation.OnTabPositionListener() {
            @Override
            public void onPositionTab(int position) {
                showFragment(position);
            }
        });
    }

    @Override
    public void initData() {
        loadMultipleRootFragment(R.id.Root_Fragment, 0, mFragments);
        showHideFragment(mFragments[0], mFragments[bottomNavigationPreposition]);
        myBottomNavigation.beanSelect(0);
        bottomNavigationPreposition = 0;
        if (!haslogin()) {
            loginDialog();
        }

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
    private void getLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(this, new OnRequestPermissionListener() {
                @Override
                public void PermissionSuccess(List<String> permissions) {
                }

                @Override
                public void PermissionFail(List<String> permissions) {
                    Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_LONG).show();
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
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.MainActivityCode);

        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                showFragment(0);
            }
        });
        login_dialogFragment.show(getSupportFragmentManager(), TAG);
    }
}
