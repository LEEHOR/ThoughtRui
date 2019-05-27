package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:56
 */
@Module
public class GaodeLocationModule {
    @Singleton
    @Provides
    public GaodeMapLocationHelper getgaodeLocation(){
        return new GaodeMapLocationHelper();
    }
}
