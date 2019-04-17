package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.dagger.components.BaseFragmentComponents;
import com.coahr.thoughtrui.mvp.view.UMPush.Fragment_Umeng;
import com.coahr.thoughtrui.mvp.view.WebView.Fragment_WebView;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_1;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_2;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_action_plan_viewPager;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_h;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceFragment_k;
import com.coahr.thoughtrui.mvp.view.home.DealerFragment;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.MainInfoFragment;
import com.coahr.thoughtrui.mvp.view.home.MyTabFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectTemplate;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_action_projects;
import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.mydata.UploadOptions;
import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStartAnswering;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewMainPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewSubjectList;
import com.coahr.thoughtrui.mvp.view.search.SearchFragment;
import com.coahr.thoughtrui.mvp.view.subjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnex;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewStartViewPager;
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

    @ContributesAndroidInjector(modules = MainInfoFragment_Module.class)
    abstract MainInfoFragment mainInfoFragment();

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

    @ContributesAndroidInjector()
    abstract ReviewedFragment ReviewFragmentInjector();

    @ContributesAndroidInjector()
    abstract ReviewMainPager ReviewPagerInjector();

    @ContributesAndroidInjector()
    abstract ReviewSubjectList ReviewInfoListInjector();

    @ContributesAndroidInjector()
    abstract ReviewStartViewPager ReviewStartProjectInjector();

    @ContributesAndroidInjector()
    abstract ReViewStartAnswering ReViewStartInjector();

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

    @ContributesAndroidInjector()
    abstract ProjectTemplate projectTemplate();

    @ContributesAndroidInjector()
    abstract DealerFragment dealerFragment();

    @ContributesAndroidInjector()
    abstract Fragment_Umeng fragment_umeng();

    @ContributesAndroidInjector()
    abstract Fragment_WebView fragment_webView();

    @ContributesAndroidInjector()
    abstract SearchFragment searchFragment();

    @ContributesAndroidInjector()
    abstract UploadOptions uploadOptions();

    @ContributesAndroidInjector()
    abstract Fragment_action_projects fragmentActionProjects();

    @ContributesAndroidInjector()
    abstract Fragment_Action_plan_presentation_1 fragment_action_plan_presentation_1();

    @ContributesAndroidInjector()
    abstract Fragment_action_plan_viewPager fragmentActionPlanViewPager();

    @ContributesAndroidInjector()
    abstract Fragment_Action_plan_presentation_2 fragment_action_plan_presentation_2();
}
