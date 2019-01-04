package com.coahr.thoughtrui.widgets.HttpUtils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Leehor
 * on 2019/1/4
 * on 14:01
 */
public class OkHttpHelper {
    private static OkHttpHelper mOkHttpHelper;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private Handler mHandler;

    private OkHttpHelper() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance() {
        if (mOkHttpHelper == null) {
            synchronized (OkHttpHelper.class) {
                mOkHttpHelper = new OkHttpHelper();
            }
        }
        return mOkHttpHelper;
    }

    public void AsyncGet(String url, OkCallBack<BaiduApi> callBack) {
        Request request = buildRequst(url, null, OkHttpMethodType.GET);
        doRequest(request, callBack);

    }

    public void AsyncPost(String url, Map<String, String> params, OkCallBack<BaiduApi> callBack) {
        Request request = buildRequst(url, params, OkHttpMethodType.POST);
        doRequest(request, callBack);
    }


    public void doRequest(final Request request, final OkCallBack<BaiduApi> callBack) {
        callBack.onPreRequest(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call,IOException e) {
                callBackFailure(call.request(),e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    if (callBack.mType == String.class) {
                        callBackSuccess(response,str,callBack);
                    } else {
                        try {
                            Object o= mGson.fromJson(str, callBack.mType);
                            callBackSuccess(response,o,callBack);
                        } catch (JsonParseException e) {
                            callBack.onError(response, null, response.code());
                        }
                    }
                } else {
                    callBack.onError(response, null, response.code());
                }
            }
        });

    }

    private Request buildRequst(String url, Map<String, String> params, OkHttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        if (type == OkHttpMethodType.GET) {
            builder.url(url)
                    .get();
        }
        if (type == OkHttpMethodType.POST) {
            builder.url(url)
                    .post(buildFormData(params));
        }

        return builder.build();
    }

    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params == null) {
            return null;
        }
        for (Map.Entry<String, String> map : params.entrySet()) {
            builder.addEncoded(map.getKey(), map.getValue());
        }
        return builder.build();
    }


    enum OkHttpMethodType {
        GET,
        POST
    }

    private void callBackSuccess(final Response response, final Object o, final OkCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response,o);
            }
        });
    }

    private void callBackFailure(final Request request, final Exception e, final OkCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(request,e);
            }
        });
    }

}
