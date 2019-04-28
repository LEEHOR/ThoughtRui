package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.dagger.components.BaseActivityComponents;
import com.coahr.thoughtrui.dagger.modules.attendance.AttendanceRootActivityModule;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceRootActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:38
 */
@Module(subcomponents = {
        BaseActivityComponents.class
})
public abstract class AllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector(modules = ConstantsActivityModule.class)
    abstract ConstantsActivity contributeConstantsActivityInjector();

    @ContributesAndroidInjector(modules = AttendanceRootActivityModule.class)
    abstract AttendanceRootActivity contributeAttachRootActivityInjector();

    @ContributesAndroidInjector(modules = StartProjectActivityModule.class)
    abstract StartProjectActivity contributeStartProjectActivityInjector();

}
