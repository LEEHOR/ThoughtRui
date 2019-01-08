package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:15
 */
@Module
public class ReviewPager_Module {
    @Provides
    public  String provideName() {
        return ReviewPager.class.getName();
    }
}
