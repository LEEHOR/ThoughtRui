package com.coahr.thoughtrui.dagger.modules.retrofit;

import android.os.Build;
import android.provider.SyncStateContract;
import android.text.TextUtils;

import com.coahr.thoughtrui.BuildConfig;
import com.coahr.thoughtrui.Utils.HttpLogging.MyHttpLogging;
import com.coahr.thoughtrui.commom.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:07
 */
@Module
public class OkHttpModule {

    private HttpLoggingInterceptor loggingInterceptor;
    @Singleton
    @Provides
    @Named("default")
    public OkHttpClient providesOkHttpClient(){
    OkHttpClient.Builder builder=new OkHttpClient.Builder();
      if (builder.interceptors() !=null){
          builder.interceptors().clear();
      }

      builder.connectTimeout(Constants.timeout,TimeUnit.SECONDS);
      builder.readTimeout(Constants.timeout,TimeUnit.SECONDS);
      builder.writeTimeout(Constants.timeout,TimeUnit.SECONDS);
    //错误重连
    builder.retryOnConnectionFailure(true);
    //DEBUG模式下配Log拦截器
    if (BuildConfig.DEBUG){
        loggingInterceptor = new HttpLoggingInterceptor(new MyHttpLogging());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    if (BuildConfig.DEBUG){
        builder.addInterceptor(loggingInterceptor);
    }
    return builder.build();
}

    @Singleton
    @Provides
    @Named("cache")
    public OkHttpClient providesCacheOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=" + 3600 * 6 + " ,max-stale=2419200";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(Constants.timeout, TimeUnit.SECONDS)
                .build();
    }
}


