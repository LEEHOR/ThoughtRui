package com.coahr.thoughtrui.widgets.HttpUtils;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Leehor
 * on 2018/11/19
 * on 12:59
 * 百度地图Api线上定位
 * 根据经纬度获取地址
 */
public class HttpUtils {
    private static HttpUtilListener listener;
    static OkHttpClient client = new OkHttpClient();

    public static void getAddress(String lat, String lot ,HttpUtilListener listener) {

        HttpUrl httpUrl = HttpUrl.parse("http://api.map.baidu.com/geocoder/v2/" +
                "?ak=zRN4LdcsWnyGqEcZdeUC5NdrfWRGIe4f" +
                "&location=" + lat + lot + "" +
                "&output=json&pois=1" +
                "&mcode=90:37:69:C2:14:27:E2:88:4C:C7:0D:AA:9A:CF:39:35:EE:78:BD:86;com.coahr.thoughtrui")
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(httpUrl.toString())
                .build();
        Response response = null;

        try {
            Response execute = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String string = execute.body().string();
                if (listener != null) {
                    listener.getAddressSuccess(string);
                }
            } else {
                if (listener != null) {
                    listener.getAddressFailure("失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.getAddressFailure(e.toString());
            }
        }



    }

    public void setListener(HttpUtilListener listener) {
        this.listener = listener;
    }
}
