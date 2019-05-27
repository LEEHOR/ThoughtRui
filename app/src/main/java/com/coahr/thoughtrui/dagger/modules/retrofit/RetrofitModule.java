package com.coahr.thoughtrui.dagger.modules.retrofit;

import android.text.TextUtils;

import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:21
 */
@Module
public class RetrofitModule {
    @Singleton
    @Provides
    public Retrofit providerDefaultRetrofit(@Named("default") OkHttpClient okHttpClient){
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Gson double类型转换, 避免空字符串解析出错
        final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
            @Override
            public Number read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                if (in.peek() == JsonToken.STRING) {
                    String tmp = in.nextString();
                    if (TextUtils.isEmpty(tmp)) tmp = "0";
                    return Double.parseDouble(tmp);
                }
                return in.nextDouble();
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                out.value(value);
            }
        };

        // Gson long类型转换, 避免空字符串解析出错
        final TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
            @Override
            public Number read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                if (in.peek() == JsonToken.STRING) {
                    String tmp = in.nextString();
                    if (TextUtils.isEmpty(tmp)) tmp = "0";
                    return Long.parseLong(tmp);
                }
                return in.nextLong();
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                out.value(value);
            }
        };

        // Gson int类型转换, 避免空字符串解析出错
        final TypeAdapter<Number> INT = new TypeAdapter<Number>() {
            @Override
            public Number read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                if (in.peek() == JsonToken.STRING) {
                    String tmp = in.nextString();
                    if (TextUtils.isEmpty(tmp)) tmp = "0";
                    return Integer.parseInt(tmp);
                }
                return in.nextInt();
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                out.value(value);
            }
        };

        final TypeAdapter<String> STRING = new TypeAdapter<String>() {
            @Override
            public void write(JsonWriter out, String value) throws IOException {
                // TODO Auto-generated method stub
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value);
            }

            @Override
            public String read(JsonReader in) throws IOException {

                // TODO Auto-generated method stub
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return "";
                }
                return in.nextString();
            }

        };

        gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(String.class,String.class,STRING));
        gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(double.class,Double.class,DOUBLE));
        gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(long.class,Long.class,LONG));
        gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(int.class,Integer.class,INT));
        return  new Retrofit.Builder()
                .baseUrl(ApiContact.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))//将call<responsebody>中的responsebody  通过gson转换为been
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//可以让apiservice中方法返回  observerble 与rxjava对接
                .build();
    }


}
