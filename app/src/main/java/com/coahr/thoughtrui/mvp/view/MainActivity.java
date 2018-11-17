package com.coahr.thoughtrui.mvp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.location.BDLocation;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ActivityManagerUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Main;
import com.coahr.thoughtrui.mvp.presenter.MainActivityP;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.MyBottomNavigation.MyBottomNavigation;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

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
    private SupportFragment[] mFragments = new SupportFragment[2];
    private long exitTime = 0;
    private static final long INTERVAL_TIME = 2000;
    private String sessionId;

    @Override
    public MainActivityC.Presenter getPresenter() {
        return p;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mFragments[0] = findFragment(MainFragment.class);
            mFragments[1] = findFragment(UploadFragment.class);
        } else {
            mFragments[0] = MainFragment.newInstance();
            mFragments[1] = UploadFragment.newInstance();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, "sessionId", null);
        myBottomNavigation.setOnTabPositionListener(new MyBottomNavigation.OnTabPositionListener() {
            @Override
            public void onPositionTab(int position) {
                if (position<=1){
                    showFragment(position);

                }
            }
        });
        loadMultipleRootFragment(R.id.Root_Fragment, 0, mFragments);
        if ( sessionId!=null){
           // loadMultipleRootFragment(R.id.Root_Fragment, 0, mFragments);
            showFragment(0);
        } else {
            Intent intent =new Intent(MainActivity.this,ConstantsActivity.class);
            intent.putExtra("from",Constants.MainActivityCode);
            intent.putExtra("to",Constants.loginFragmentCode);
            intent.putExtra("type",1);
            startActivityForResult(intent,100);
        }
    }

    @Override
    public void initData() {
        if (sessionId !=null) {
           // p.startLocation();
        }
    }
    private void showFragment(int position) {
        showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
        myBottomNavigation.beanSelect(position);
        bottomNavigationPreposition = position;
    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        KLog.d("定位成功activity",location.getAddrStr());
        Toast.makeText(BaseApplication.mContext,location.getAddrStr(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationFailure(int failure) {
        KLog.d("定位失败activity",failure);
        Toast.makeText(BaseApplication.mContext,failure+"",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100 && requestCode==100){
            showFragment(0);
           // p.startLocation();
            EventBus.getDefault().postSticky(new Event_Main(1,"登陆成功"));
        }
    }
    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > INTERVAL_TIME) {
            ToastUtils.showLong(getResources().getString(R.string.carsuper_exit));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            if (Constants.isKill){
                ActivityManagerUtils.getInstance().appExit();
            } else {
                PreferenceUtils.remove(BaseApplication.mContext,Constants.sessionId_key);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
