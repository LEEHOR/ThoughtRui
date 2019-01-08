package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.dagger.components.BaseFragmentComponents;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.SubjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewStartProject;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;
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
    @ContributesAndroidInjector()
    abstract MyTabFragment contributeMyFirstFragmentInjector();

    @ContributesAndroidInjector(modules = UploadFragmentModule.class)
    abstract UploadFragment contributeUploadFragmentInjector();

    @ContributesAndroidInjector(modules = MyMainFragmentModule.class)
    abstract MainFragment MainFragmentInjector();

    @ContributesAndroidInjector()
    abstract LoginFragment LoginFragmentInjector();

    @ContributesAndroidInjector()
    abstract ProjectDetailFragment ProjectDetailFragmentInjector();

    @ContributesAndroidInjector()
    abstract AttendanceFragment_k AttachFragment_kFragmentInjector();

    @ContributesAndroidInjector()
    abstract AttendanceFragment_h AttachFragment_hFragmentInjector();

    @ContributesAndroidInjector()
    abstract PagerFragment_a PagerFragment_aFragmentInjector();

    @ContributesAndroidInjector()
    abstract DialogFragmentAudioPlay DialogFragmentAudioPlayInjector();

    @ContributesAndroidInjector()
    abstract Fragment_Topics Fragment_TopicsInjector();

    @ContributesAndroidInjector(modules = ReviewFragment_Module.class)
    abstract ReviewedFragment ReviewFragmentInjector();

    @ContributesAndroidInjector()
    abstract ReviewPager ReviewPagerInjector();

    @ContributesAndroidInjector()
    abstract ReviewInfoList ReviewInfoListInjector();

    @ContributesAndroidInjector()
    abstract ReviewStartProject  ReviewStartProjectInjector();
}
