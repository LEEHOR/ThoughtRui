package com.coahr.thoughtrui.widgets.HttpUtils;

import android.os.Handler;
import android.os.Looper;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.google.gson.Gson;

import java.io.IOException;

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
 * on 12:59
 */
public class BaiduApi {
    private  OkHttpClient okHttpClient;
    private String input_url;
    private String input_AK;
    private String input_output;
    private String input_pois;
    private String input_mcode;
    private String input_lat;
    private String input_lot;
    private Class input_bean;
    private  HttpUtilListener listener;


    public BaiduApi(Builder builder) {
        this.input_url = builder.default_url;
        this.input_AK = builder.default_AK;
        this.input_output = builder.default_output;
        this.input_pois = builder.default_pois;
        this.input_mcode = builder.default_mcode;
        this.input_lat = builder.default_lat;
        this.input_lot = builder.default_lot;
        this.input_bean=builder.default_bean;
        this.listener=builder.httpUtilListener;
        OkHttpMethodType default_method = builder.default_method;
        //1.初始化client
        if (okHttpClient==null) {
            okHttpClient = new OkHttpClient();
        }
        //2.初始化请求体
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("ak", input_AK)
                .addEncoded("location", input_lat + "," + input_lot)
                .addEncoded("output", input_output)
                .addEncoded("pois", input_pois)
                .addEncoded("mcode", input_mcode)
                .build();
        //3.初始化请求对象
        Request request = new Request.Builder()
                .url(input_url)
                .post(requestBody)
                .build();
        //4.执行请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                    if (listener != null) {
                                listener.getAddressFailure(e.toString());
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                    if (listener != null) {
                                if (response.isSuccessful()) {
                                    listener.getAddressSuccess(response);
                    }
                }
            }
        });
    }

    public static class Builder {
        private OkHttpMethodType default_method=OkHttpMethodType.POST;
        private String default_url = "http://api.map.baidu.com/geocoder/v2/";
        private String default_AK = "zRN4LdcsWnyGqEcZdeUC5NdrfWRGIe4f";
        private String default_output = "json";
        private String default_pois = "1";
        private String default_mcode = "90:37:69:C2:14:27:E2:88:4C:C7:0D:AA:9A:CF:39:35:EE:78:BD:86;com.coahr.thoughtrui";
        private String default_lat;
        private String default_lot;
        private Class default_bean;
        private HttpUtilListener httpUtilListener;

        public BaiduApi build() {
            return new BaiduApi(this);
        }

        public Builder Url(String url) {
            this.default_url = url;
            return this;
        }

        public Builder AK(String AK) {
            this.default_AK = AK;
            return this;
        }

        public Builder default_output(String output) {
            this.default_output = output;
            return this;
        }

        public Builder default_mcode(String mcode) {
            this.default_mcode = mcode;
            return this;
        }

        public Builder default_pois(String pois) {
            this.default_pois = pois;
            return this;
        }

        public Builder default_lat(String lat) {
            this.default_lat = lat;
            return this;
        }

        public Builder default_lot(String lot) {
            this.default_lot = lot;
            return this;
        }
        public Builder default_methodType(OkHttpMethodType type) {
            this.default_method = type;
            return this;
        }
        public Builder default_bean(Class type) {
            this.default_bean = type;
            return this;
        }
        public Builder CallBack(HttpUtilListener httpUtilListeners) {
            this.httpUtilListener = httpUtilListeners;
            return this;
        }

    }
    public enum OkHttpMethodType {
        GET,
        POST
    }
}