package com.coahr.thoughtrui.dagger.components;

import com.coahr.thoughtrui.dagger.modules.AllActivityModule;
import com.coahr.thoughtrui.dagger.modules.AllFragmentModule;
import com.coahr.thoughtrui.dagger.modules.BaiduLocationModule;
import com.coahr.thoughtrui.dagger.modules.retrofit.ApiModule;
import com.coahr.thoughtrui.dagger.modules.retrofit.OkHttpModule;
import com.coahr.thoughtrui.dagger.modules.retrofit.RetrofitModule;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 13:35
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AllActivityModule.class,
        AllFragmentModule.class,
        ApiModule.class,
        OkHttpModule.class,
        RetrofitModule.class,
        BaiduLocationModule.class
})
public  interface ApplicationComponents {

        void inject(BaseApplication application);

}
