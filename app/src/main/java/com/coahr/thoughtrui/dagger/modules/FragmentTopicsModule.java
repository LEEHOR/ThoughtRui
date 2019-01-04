package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.SubjectList.Fragment_Topics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 16:02
 */
@Module
public class FragmentTopicsModule {
    @Provides
    public  String provideName() {
        return Fragment_Topics.class.getName();
    }
}
