package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.dagger.components.BaseFragmentComponents;
import com.coahr.thoughtrui.dagger.modules.attendance.AttendanceFragment_hModule;
import com.coahr.thoughtrui.dagger.modules.attendance.AttendanceFragment_kModule;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.startProject.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;
import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:39
 */
@Module(subcomponents = {
        BaseFragmentComponents.class
})
public abstract class AllFragmentModule {
    @ContributesAndroidInjector(modules = MyTabFragmentModule.class)
    abstract MyTabFragment contributeMyFirstFragmentInjector();

    @ContributesAndroidInjector(modules = UploadFragmentModule.class)
    abstract UploadFragment contributeUploadFragmentInjector();

    @ContributesAndroidInjector(modules = MyMainFragmentModule.class)
    abstract MainFragment MainFragmentInjector();

    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment LoginFragmentInjector();

    @ContributesAndroidInjector()
    abstract ProjectDetailFragment ProjectDetailFragmentInjector();

    @ContributesAndroidInjector(modules = AttendanceFragment_kModule.class)
    abstract AttendanceFragment_k AttachFragment_kFragmentInjector();

    @ContributesAndroidInjector(modules = AttendanceFragment_hModule.class)
    abstract AttendanceFragment_h AttachFragment_hFragmentInjector();

    @ContributesAndroidInjector(modules = StartProjectFragmentModule.class)
    abstract StartProjectFragment StartProjectFragmentFragmentInjector();

    @ContributesAndroidInjector(modules = PagerStartProjectFragmentModule.class)
    abstract PagerFragment_a PagerFragment_aFragmentInjector();

    @ContributesAndroidInjector(modules = DialogFragmentAudioPaly_Module.class)
    abstract DialogFragmentAudioPlay DialogFragmentAudioPlayInjector();

    @ContributesAndroidInjector()
    abstract Fragment_Topics Fragment_TopicsInjector();
}
