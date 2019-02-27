package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.reviewed.ReviewMainPager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:15
 */
@Module
public class ReviewMainPager_Module {
    @Provides
    public  String provideName() {
        return ReviewMainPager.class.getName();
    }
}
