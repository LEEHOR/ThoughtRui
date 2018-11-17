package com.coahr.thoughtrui.dagger.components;

import com.coahr.thoughtrui.mvp.Base.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 15:10
 */
@Subcomponent(modules = {
        AndroidInjectionModule.class,
})
public interface BaseActivityComponents extends AndroidInjector<BaseActivity> {
    //每一个继承BaseActivity的Activity，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {
    }
}
