package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:56
 */
@Module
public class BaiduLocationModule {
    @Singleton
    @Provides
    public BaiduLocationHelper getBaiduLocation(){
        return new BaiduLocationHelper();
    }
}
