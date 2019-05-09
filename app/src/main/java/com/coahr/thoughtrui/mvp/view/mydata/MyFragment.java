package com.coahr.thoughtrui.mvp.view.mydata;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment_not_padding;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.CircularImageView;

import java.io.File;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：我的页面
 */
public class MyFragment extends BaseFragment_not_padding implements View.OnClickListener {
    @BindView(R.id.my_header)
    CircularImageView my_header; //用户头像
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;  //用户名
    @BindView(R.id.tv_dxz)
    TextView tv_dxz; //  待下载
    @BindView(R.id.tv_dsc)
    TextView tv_dsc; //待上传
    @BindView(R.id.tv_dks)
    TextView tv_dks; //带开始‘
    @BindView(R.id.tv_wwc)
    TextView tv_wwc;//未完成
    @BindView(R.id.re_mytop)
    RelativeLayout re_mytop;

    @BindView(R.id.re_action_plan) //行动报表
    RelativeLayout reActionPlan;

    @BindView(R.id.re_message_center)
    RelativeLayout re_message_center;  //消息中心
    @BindView(R.id.tv_xx_count)
    TextView tv_xx_count; //消息中心个数

    @BindView(R.id.re_upload_options)
    RelativeLayout re_upload_options; //数据上传设置

    @BindView(R.id.tv_cckj)
    TextView tv_cckj; //数据存储

    @BindView(R.id.re_clear_cache)
    RelativeLayout re_clear_cache; //清除缓存
    @BindView(R.id.tv_qchc)
    TextView tv_qchc; //缓存大小

    @BindView(R.id.re_change_pass)
    RelativeLayout re_change_pass; //修改密码

    @BindView(R.id.re_feed_back)
    RelativeLayout re_feed_back; //帮助与反馈

    @BindView(R.id.tv_quit_account)
    TextView tv_quit_account; //退出登录

    private int unLoad, unStart, unUpload, unComplete;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        ScreenUtils.setMargins(re_mytop, 20, ScreenUtils.getStatusBarHeight(BaseApplication.mContext), 20, 0);
        re_message_center.setOnClickListener(this);
        re_upload_options.setOnClickListener(this);
        re_clear_cache.setOnClickListener(this);
        re_change_pass.setOnClickListener(this);
        re_feed_back.setOnClickListener(this);
        tv_quit_account.setOnClickListener(this);
        reActionPlan.setOnClickListener(this);
        getAudioPermission();
    }

    @Override
    public void initData() {
        featchProjectInfo(tv_dxz, tv_dsc, tv_dks, tv_wwc);
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.user_key)) {
            String user_name = PreferenceUtils.getPrefString(_mActivity, Constants.user_key, "");
            tv_user_name.setText(user_name);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_action_plan: //行动报表
                goOtherPage(ConstantsActivity.class, Constants.fragment_myFragment, Constants.fragment_action_pan);
                break;
            case R.id.re_message_center: //消息中心
                goOtherPage(ConstantsActivity.class, Constants.fragment_myFragment, Constants.fragment_umeng);
                break;
            case R.id.re_upload_options: //数据上传设置
                goOtherPage(ConstantsActivity.class, Constants.fragment_myFragment, Constants.fragment_uploadOptions);
                break;
            case R.id.re_clear_cache: //清除缓存
                ClearDataDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_9));
                break;
            case R.id.re_change_pass: //修改密码
                goOtherPage(ConstantsActivity.class, Constants.fragment_myFragment, Constants.fragment_ChangePass);
                break;
            case R.id.re_feed_back: //帮助与反馈
                goOtherPage(ConstantsActivity.class, Constants.fragment_myFragment, Constants.fragment_feedback);
                break;
            case R.id.tv_quit_account:  //退出登录
                if (haslogin()) {
                    showDialog();
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_10));
                }
                break;
        }
    }

    private void goOtherPage(Class c, int from, int to) {
        Intent intent = new Intent(_mActivity, c);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        startActivity(intent);
    }

    /**
     * 获取可用空间/总大小
     *
     * @return
     */
    public String readSDCard() {
        String state = Environment.getExternalStorageState();
        String storageSize;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            //总大小
            long blockSize = sf.getBlockSizeLong();
            //获取内存卡上的blockCount
            long blockCount = sf.getBlockCountLong();
            //可用大小
            long availCount = sf.getAvailableBlocksLong();
            String freeSize = formatDouble(blockSize * blockCount / 1024 / 1024 / 1024 - availCount * blockSize / 1024 / 1024 / 1024);
            String TotalSize = formatDouble(blockSize * blockCount / 1024 / 1024 / 1024);
            storageSize = freeSize + "GB" + "/" + TotalSize + "GB";
            return storageSize;
        }
        return getResources().getString(R.string.phrases_25);
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        return nf.format(d);
    }

    /**
     * 动态获取录音权限
     */
    private void getAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                            String s = readSDCard();
                            tv_cckj.setText(s);
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            tv_cckj.setText(getResources().getString(R.string.phrases_27));
                        }

                        @Override
                        public void PermissionHave() {
                            String s = readSDCard();
                            tv_cckj.setText(s);
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            String s = readSDCard();
            tv_cckj.setText(s);
        }
    }

    /**
     * 获取当前数目
     *
     * @param star
     * @param download
     * @param upload
     * @param complete
     */
    public void featchProjectInfo(TextView star, TextView download, TextView upload, TextView complete) {
        unLoad = 0;
        unStart = 0;
        unUpload = 0;
        unComplete = 0;
        if (haslogin()) {
            List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
            if (usersDBS != null && usersDBS.size() > 0) {
                List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
                if (projectsDBSList != null && projectsDBSList.size() > 0) {
                    for (int i = 0; i < projectsDBSList.size(); i++) {
                        if (projectsDBSList.get(i).getIsDeletes() != 1) {
                            if (projectsDBSList.get(i).getDownloadTime() == -1) {
                                unLoad++;
                            }
                            if (projectsDBSList.get(i).getCompleteStatus() == 1 && projectsDBSList.get(i).getpUploadStatus() == 0) {
                                unUpload++;
                            }
                            if (projectsDBSList.get(i).getIsComplete() == 0) {
                                unComplete++;
                            }
                            if (projectsDBSList.get(i).getSubjectsDBList() == null) {
                                unStart++;
                            }
                        }

                    }

                }
            }
        }
        download.setText(String.valueOf(unLoad));
        upload.setText(String.valueOf(unUpload));
        complete.setText(String.valueOf(unComplete));
        star.setText(String.valueOf(unStart));
        if (unLoad+unComplete+unStart+unUpload > 0) {
            tv_xx_count.setVisibility(View.VISIBLE);
            tv_xx_count.setText((unLoad+unComplete+unStart+unUpload) + "");
        } else {
            tv_xx_count.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 退出登录
     */
    private void quitAccount() {
        Constants.wan_ka = null;
        Constants.zao_ka = null;
        Constants.DbProjectId = null;
        Constants.ht_ProjectId = null;
        Constants.user_name = "";
        Constants.sessionId = "";
        tv_dxz.setText(String.valueOf(0));
        tv_dsc.setText(String.valueOf(0));
        tv_wwc.setText(String.valueOf(0));
        tv_dks.setText(String.valueOf(0));
        tv_user_name.setText("");
        PreferenceUtils.remove(BaseApplication.mContext, Constants.user_key);
        PreferenceUtils.remove(BaseApplication.mContext, Constants.sessionId_key);
        loginDialog();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (tv_dxz != null && tv_dsc != null && tv_dks != null && tv_wwc != null) {
                featchProjectInfo(tv_dxz, tv_dsc, tv_dks, tv_wwc);
            }
            if (PreferenceUtils.contains(BaseApplication.mContext, Constants.user_key)) {
                String user_name = PreferenceUtils.getPrefString(_mActivity, Constants.user_key, "");
                tv_user_name.setText(user_name);
            }
        }
    }

    /**
     * 是否退出登录
     */
    private void showDialog() {
        new MaterialDialog.Builder(_mActivity)
                .title(getResources().getString(R.string.dialog_tittle_7))
                .content(getResources().getString(R.string.dialog_content_10))
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        quitAccount();
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.fragment_myFragment);
        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                featchProjectInfo(tv_dxz, tv_dsc, tv_dks, tv_wwc);
                tv_user_name.setText(Constants.user_name);
            }
        });
        login_dialogFragment.show(getFragmentManager(), TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 清除缓存
     *
     * @param title
     * @param Content
     */
    private void ClearDataDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .cancelable(false)
                .canceledOnTouchOutside(false)
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
                    }
                }).build().show();
    }


}
