package com.coahr.thoughtrui.widgets.HttpUtils;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Leehor
 * on 2019/1/4
 * on 14:02
 */
public abstract class OkCallBack<T> {
    public Type mType;
    static Type getSupperClassTypeParameter(Class<?> subclass) {
        Type supperClass = subclass.getGenericSuperclass();
        if (supperClass instanceof Class) {
            throw new RuntimeException("Missing Type paramter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) supperClass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public OkCallBack(){
        mType=getSupperClassTypeParameter(getClass());
    }
    public abstract void onPreRequest(Request request);

    public abstract void onFailure(Request request, Exception e);

    public abstract void onSuccess(Response response, T t);

    public abstract void onError(Response response, Exception e, int code);

}
