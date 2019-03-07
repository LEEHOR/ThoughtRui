package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.search.SearchFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 18:11
 */
@Module
public class FragmentSearch_Module {
    @Provides
    public  String provideName() {
        return SearchFragment.class.getName();
    }
}
