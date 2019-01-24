package com.coahr.thoughtrui.mvp.Base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.ActivityManager;
import com.coahr.thoughtrui.commom.Constants;
import com.gyf.barlibrary.ImmersionBar;
import com.taobao.sophix.SophixManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 21:42
 */
public abstract class BaseSupportActivity extends SupportActivity {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    private Unbinder unbinder;

    public abstract int binLayout();

    public abstract void initView();

    public abstract void initData();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);  //一处声明，处处依赖注入
        super.onCreate(savedInstanceState);
        setContentView(binLayout());

        KeyBoardUtils.UpdateUI(getWindow().getDecorView().getRootView(), this);
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        getWindow().setBackgroundDrawableResource(R.drawable.bg_fff_background);
        unbinder = ButterKnife.bind(this);
        ActivityManager.getInstance().addActivity(this);
        initView();
        initData();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManager.getInstance().removeActivity(this);
        ImmersionBar.with(this).destroy();
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
