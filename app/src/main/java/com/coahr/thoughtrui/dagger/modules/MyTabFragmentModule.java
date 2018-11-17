package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 16:02
 */
@Module
public class MyTabFragmentModule {
    @Provides
    public  String provideName() {
        return MyTabFragment.class.getName();
    }
}
