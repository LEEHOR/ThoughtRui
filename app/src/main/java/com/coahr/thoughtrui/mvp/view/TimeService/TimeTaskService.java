package com.coahr.thoughtrui.mvp.view.TimeService;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.coahr.thoughtrui.BuildConfig;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.Utils.HttpLogging.MyHttpLogging;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.commom.RxManager;
import com.coahr.thoughtrui.dagger.modules.retrofit.RetrofitModule;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/14
 * 描述：
 */
public class TimeTaskService extends IntentService {
    public static final String TAG = "TimeTaskService";
    private OkHttpClient.Builder builder;
    private HttpLoggingInterceptor loggingInterceptor;

    public TimeTaskService() {
        super(TAG);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        KLog.d(TAG,action);
        if ("TIMER_ACTION".equals(action)){
            getProjectList();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent,startId);
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        AlarmTimerUtil.getInstance(BaseApplication.mContext).getUpAlarmManagerWorkOnReceiver(this);

        return START_STICKY;
    }

    /**
     * 获取项目
     */
    private void getProjectList() {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                for (int i = 0; i < projectsDBSList.size(); i++) {
                    if (projectsDBSList.get(i).getIsComplete()==1 && projectsDBSList.get(i).getpUploadStatus()==0) {
                        Intent intent = new Intent(this, TimeTaskReceiver.class);
                        intent.putExtra("type",1);
                        intent.putExtra("ContentTittle","[任务上传]");
                        intent.putExtra("ContentText","你有项目未上传，经销商代码为：");
                        intent.putExtra("PCode",projectsDBSList.get(i).getSale_code()!=null?projectsDBSList.get(i).getSale_code():projectsDBSList.get(i).getService_code());
                        intent.setAction("TIMER_ACTION");
                        sendBroadcast(intent);
                    }
                    if (projectsDBSList.get(i).getModifyTime()<=System.currentTimeMillis()){
                        Intent intent = new Intent(this, TimeTaskReceiver.class);
                        intent.putExtra("type",2);
                        intent.putExtra("ContentTittle","[超期提醒]");
                        intent.putExtra("ContentText","你有项目已超期，经销商代码为：");
                        intent.putExtra("PCode",projectsDBSList.get(i).getSale_code()!=null?projectsDBSList.get(i).getSale_code():projectsDBSList.get(i).getService_code());
                        intent.setAction("TIMER_ACTION");
                        sendBroadcast(intent);
                    }
                }
            }
        }
        getUpdatePost();
    }


    private void getUpdatePost(){
        if (builder == null) {
            builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG){
                loggingInterceptor = new HttpLoggingInterceptor(new MyHttpLogging());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            if (BuildConfig.DEBUG){
                builder.addInterceptor(loggingInterceptor);
            }
        }
        //2.初始化请求体
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("sessionId", Constants.sessionId)
                .addEncoded("status", "-1")
                .build();
        //3.初始化请求对象
        Request request = new Request.Builder()
                .url("http://leinuo.coahr.com:8085/research/app/censor/list.htm")
                .post(requestBody)
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    CensorBean censorBean = gson.fromJson(string, CensorBean.class);
                    if (censorBean.getResult()==1) {
                        List<CensorBean.DataBean.ListBean> list = censorBean.getData().getList();
                        if (list != null && list.size()>0) {
                            for (int i = 0; i <list.size() ; i++) {
                                Intent intent = new Intent(BaseApplication.mContext, TimeTaskReceiver.class);
                                intent.putExtra("type",2);
                                intent.putExtra("ContentTittle","[审核未通过]");
                                intent.putExtra("ContentText","你有项目审核未通过，经销商代码为：");
                                intent.putExtra("PCode",list.get(i).getService_code());
                                intent.setAction("TIMER_ACTION");
                                sendBroadcast(intent);
                            }

                        }
                    }
                }
            }
        });
    }
}
