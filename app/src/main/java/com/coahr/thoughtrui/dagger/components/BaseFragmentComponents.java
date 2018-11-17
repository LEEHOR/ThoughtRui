package com.coahr.thoughtrui.dagger.components;

import com.coahr.thoughtrui.mvp.Base.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 15:35
 */
@Subcomponent(modules = {
        AndroidInjectionModule.class,
})
public interface BaseFragmentComponents extends AndroidInjector<BaseFragment>  {

    //每一个继承BaseFragment的fragment，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment> {
    }
}
