package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.home.DealerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class Fragment_DealerSelect_Module {
    @Provides
    public  String provideName() {
        return DealerFragment.class.getName();
    }
}
