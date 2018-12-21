package com.coahr.thoughtrui.mvp.view;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;

/**
 * Created by Leehor
 * on 2018/11/17
 * on 8:28
 * 开始访问页面
 */
public class StartProjectActivity extends BaseSupportActivity {
    @Override
    public int binLayout() {
        return R.layout.activity_startproject;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        loadRootFragment(R.id.start_project_root,StartProjectFragment.newInstance());
    }

    @Override
    public void onBackPressedSupport() {
       showDialog("提示","退出答题");
    }

    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        }).build().show();
    }
}
