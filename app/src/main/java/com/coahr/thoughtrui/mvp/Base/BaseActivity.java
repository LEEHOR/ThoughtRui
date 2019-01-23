package com.coahr.thoughtrui.mvp.Base;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.ActivityManager;
import com.coahr.thoughtrui.commom.Constants;
import com.gyf.barlibrary.ImmersionBar;

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
