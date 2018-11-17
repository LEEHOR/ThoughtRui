package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 16:02
 */
@Module
public class MyMainFragmentModule {
    @Provides
    public  String provideName() {
        return MainFragment.class.getName();
    }
}
