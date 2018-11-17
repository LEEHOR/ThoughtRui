package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 21:54
 */
@Module
public class StartProjectFragmentModule {
    @Provides
    public  String provideName() {
        return StartProjectFragment.class.getName();
    }
}
