package com.coahr.thoughtrui.Utils.UpdateVersion;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.dagger.modules.retrofit.OkHttpModule;
import com.coahr.thoughtrui.dagger.modules.retrofit.RetrofitModule;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.coahr.thoughtrui.mvp.model.ApiService;
import com.coahr.thoughtrui.mvp.model.Bean.UpdateBean;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CheckVersion {

    private volatile Retrofit retrofit;

    private MaterialDialog materialDialog;
    private Context mContext;
    private String downloadUrl;
    private Map<String, Object> params = new HashMap<>();
    private DownloadManager downloadManager;

    public CheckVersion(Context context) {
        this.mContext = context;
        initMaterialDialog(mContext);
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 初始化更新dialog
     */
    private void initMaterialDialog(Context context) {
        materialDialog = new MaterialDialog.Builder(context)
//				.customView(R.layout.dialog_material,false)
                .title("温馨提示")
                .titleGravity(GravityEnum.CENTER)
                .content("有新版本更新哦！")
                .contentGravity(GravityEnum.CENTER)
                .neutralText("去更新")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        downloadApk();
                        materialDialog.dismiss();
                    }
                }).negativeText("取消")
                .build();
    }

    /**
     * 显示mainactivity的更新dialog
     */
    public void showMaterialDialog() {
        materialDialog.show();
    }


    /**
     * 执行版本更新检查
     */
    public void check() {
        params.put("dsad", "android");
        params.put("", getAppInfo());
        KLog.d("版本", getAppInfo());
        getApiService().updatecheck(params).enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                KLog.d("版本", "1111111111111111111111");
                if (response.isSuccessful()) {
                    UpdateBean updateBean = response.body();
                    if (updateBean.getResult() == 1 && updateBean.getIsnew() == 1) {
                        downloadUrl = updateBean.getUrl();
                        showMaterialDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                KLog.d("版本", "22222222222222222");
            }
        });


    }


    public void downloadApk() {
        File file = new File(Constants.SAVE_DIR_BASE, downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1));
        Uri uri = Uri.fromFile(file);
        if (file.exists()) {
            file.delete();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        request.setTitle(mContext.getResources().getString(R.string.app_name));
        request.setDescription(mContext.getResources().getString(R.string.download_apk));
        request.setDestinationUri(uri);
        downloadManager.enqueue(request);
//		long id = downloadManager.enqueue(request);
//		PreferenceUtils.setPrefLong(mContext, Constants.ID, id);
        PreferenceUtils.setPrefString(mContext, Constants.URL, file.getAbsolutePath());
    }

    private String getAppInfo() {
        try {
            String pkName = mContext.getPackageName();
            return mContext.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
        } catch (Exception e) {
        }
        return null;
    }


    public ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiContact.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
