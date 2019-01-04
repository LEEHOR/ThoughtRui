package com.coahr.thoughtrui.widgets.HttpUtils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Leehor
 * on 2018/11/19
 * on 13:01
 */
public interface HttpUtilListener {
    void getAddressSuccess(Response response);

    void getAddressFailure(String e);
}
