package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.dagger.components.BaseFragmentComponents;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;
import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.SubjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnex;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStart;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewStartPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewedFragment;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.Fill_in_blankDialog;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;

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

    @ContributesAndroidInjector(modules = MyFragment_Module.class)
    abstract MyFragment MyFragmentInjector();

    @ContributesAndroidInjector()
    abstract LoginFragment LoginFragmentInjector();

    @ContributesAndroidInjector()
    abstract Login_DialogFragment Login_DialogFragmenInjector();

    @ContributesAndroidInjector()
    abstract Fill_in_blankDialog Fill_in_blankDialogInjector();

    @ContributesAndroidInjector(modules = ProjectDetailFragment_Module.class)
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
    abstract ReviewStartPager ReviewStartProjectInjector();

    @ContributesAndroidInjector()
    abstract ReViewStart ReViewStartInjector();

    @ContributesAndroidInjector()
    abstract ChangePasswordFragment ChangePasswordFragmentInjector();

    @ContributesAndroidInjector()
    abstract FragmentAnnexViewPager FragmentAnnexViewPagerInjector();

    @ContributesAndroidInjector()
    abstract FragmentAnnex FragmentAnnexInjector();

    @ContributesAndroidInjector()
    abstract ProjectSuccessDialog  ProjectSuccessDialogInjector();

    @ContributesAndroidInjector()
    abstract Fragment_Feedback Fragment_FeedbackInjector();
}
