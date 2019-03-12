package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.mydata.UploadOptions;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class Fragment_UploadOptions_Module {
    @Provides
    public  String provideName() {
        return UploadOptions.class.getName();
    }
}
