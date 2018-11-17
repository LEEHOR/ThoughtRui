package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 18:19
 */
@Module
public class PagerStartProjectFragmentModule {
    @Provides
    public  String provideName() {
        return PagerFragment_a.class.getName();
    }
}
