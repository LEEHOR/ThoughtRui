package com.coahr.thoughtrui.dagger.modules.retrofit;

import com.coahr.thoughtrui.mvp.model.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 15:58
 */
@Module
public class ApiModule {
    @Singleton
    @Provides
    public ApiService getApiService(Retrofit retrofit){
        return  retrofit.create(ApiService.class);
    }
}
