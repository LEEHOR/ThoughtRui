package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/17
 * on 8:40
 */
@Module
public class FragmentAnnexViewPagerModule {
    @Provides
    public  String provideName() {
        return FragmentAnnexViewPager.class.getName();
    }
}
