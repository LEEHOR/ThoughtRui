package com.coahr.thoughtrui.mvp.view;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;

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
import com.coahr.thoughtrui.mvp.model.Bean.Event_Main;
import com.coahr.thoughtrui.mvp.presenter.MainActivityP;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.MyBottomNavigation.MyBottomNavigation;
import com.socks.library.KLog;
import com.taobao.sophix.SophixManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

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

    @Override
    public MainActivityC.Presenter getPresenter() {
        return p;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mFragments[0] = findFragment(MainFragment.class);
            mFragments[1] = findFragment(UploadFragment.class);
            mFragments[2] = findFragment(ReviewedFragment.class);
            mFragments[3] = findFragment(MyFragment.class);
        } else {
            mFragments[0] = MainFragment.newInstance();
            mFragments[1] = UploadFragment.newInstance();
            mFragments[2] = ReviewedFragment.newInstance();
            mFragments[3] = MyFragment.newInstance();
        }
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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
        /**
         * 阿里云热更新监听
         */
        BaseApplication.setListener(new BaseApplication.MsgDisplayListener() {
            @Override
            public void CODE_DOWNLOAD_SUCCESS(String appVersion,String Info, int HandlePatchVersion) {

            }

            @Override
            public void CODE_LOAD_RELAUNCH(final String appVersion, final String Info, final int HandlePatchVersion) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialog("卡速宝更新补丁","当前版本：" + appVersion + "\n" + "\t\t" + "补丁批号" + HandlePatchVersion + "\n" + "\t\t" + Info);
                    }
                });
            }

            @Override
            public void CODE_LOAD_MFITEM(String appVersion,String Info, int HandlePatchVersion) {

            }

            @Override
            public void Other(String appVersion,int code, String Info, int HandlePatchVersion) {

            }
        });
    }

    @Override
    public void initData() {
        loadMultipleRootFragment(R.id.Root_Fragment, 0, mFragments);
        showFragment(0);
    }

    private void showFragment(int position) {
        page = position;
        if (!haslogin()) {
            Intent intent = new Intent(MainActivity.this, ConstantsActivity.class);
            intent.putExtra("from", Constants.MainActivityCode);
            intent.putExtra("to", Constants.loginFragmentCode);
            intent.putExtra("type", 1);
            startActivity(intent);
        } else {
            showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
            myBottomNavigation.beanSelect(position);
            bottomNavigationPreposition = position;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && requestCode == 100) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(EvenBus_LoginSuccess loginSuccess) {
        if (loginSuccess.getLoginType() == 100) {
            //sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, "sessionId", null);
            showFragment(0);
            // p.startLocation();
            EventBus.getDefault().postSticky(new Event_Main(1, "登陆成功", page));
        }
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > INTERVAL_TIME) {
            ToastUtils.showLong(getResources().getString(R.string.carsuper_exit));
            exitTime = System.currentTimeMillis();
        } else {
           // PreferenceUtils.remove(BaseApplication.mContext, Constants.sessionId_key);
            if (PreferenceUtils.contains(BaseApplication.mContext,Constants.AliYunHot_key)) {
                prefBoolean = PreferenceUtils.getPrefBoolean(BaseApplication.mContext, Constants.AliYunHot_key, false);
                PreferenceUtils.remove(BaseApplication.mContext,  Constants.AliYunHot_key);
            }
            finish();
            if (prefBoolean) {
                SophixManager.getInstance().killProcessSafely();
            } else {
                ActivityManagerUtils.getInstance().appExit();
            }
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
                    SophixManager.getInstance().queryAndLoadNewPatch();
                }

                @Override
                public void PermissionFail(List<String> permissions) {
                    Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void PermissionHave() {
                    SophixManager.getInstance().queryAndLoadNewPatch();
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE);

        } else {
            SophixManager.getInstance().queryAndLoadNewPatch();
        }
    }

    /**
     *
     * @param title
     * @param Content
     */
    private void Dialog(String title, String Content) {
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
                        dialog.dismiss();
                        SophixManager.getInstance().killProcessSafely();

                    }
                }).build().show();
    }
}
