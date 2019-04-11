package com.coahr.thoughtrui.mvp.view;

import android.os.Bundle;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.view.UMPush.Fragment_Umeng;
import com.coahr.thoughtrui.mvp.view.WebView.Fragment_WebView;
import com.coahr.thoughtrui.mvp.view.home.DealerFragment;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectTemplate;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;
import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.mydata.UploadOptions;
import com.coahr.thoughtrui.mvp.view.search.SearchFragment;
import com.coahr.thoughtrui.mvp.view.subjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewSubjectList;

import androidx.annotation.Nullable;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 21:40
 * 中间跳转页面
 */
public class ConstantsActivity extends BaseSupportActivity {


    @Override
    public int binLayout() {
        return R.layout.activity_constants;
    }

    @Override
    public void initView() {
        switch (getIntent().getIntExtra("to", 0)) {
            case Constants.loginFragmentCode: //登录页
                loadRootFragment(R.id.Constants_Fragment, LoginFragment.newInstance(getIntent().getIntExtra("type", 0), getIntent().getIntExtra("from", -1)));
                break;
            case Constants.ProjectDetailFragmentCode://项目详情页
                loadRootFragment(R.id.Constants_Fragment, ProjectDetailFragment.newInstance(
                          getIntent().getStringExtra("projectId")
                        , getIntent().getStringExtra("templateId")
                        , getIntent().getStringExtra("dealerId")
                        , getIntent().getIntExtra("type", 0)));
                break;
//            case Constants.startProjectFragment: //开始访问页
//                loadRootFragment(R.id.Constants_Fragment, StartProjectFragment.newInstance());
//                break;
            case Constants.fragment_topics: //题目列表
                loadRootFragment(R.id.Constants_Fragment, Fragment_Topics.newInstance());
                break;
            case Constants.fragment_ChangePass://修改密码
                loadRootFragment(R.id.Constants_Fragment, ChangePasswordFragment.newInstance());
                break;
            case Constants.fragment_AnnexViewPager: //项目附件
                loadRootFragment(R.id.Constants_Fragment, FragmentAnnexViewPager.newInstance());
                break;
            case Constants.fragment_review_list:  //审核列表
                loadRootFragment(R.id.Constants_Fragment, ReviewSubjectList.newInstance(getIntent().getStringExtra("projectId"), getIntent().getStringExtra("sessionId"), getIntent().getIntExtra("type", 0)));
                break;
            case Constants.fragment_feedback://帮助与反馈
                loadRootFragment(R.id.Constants_Fragment, Fragment_Feedback.newInstance());
                break;
            case Constants.fragment_main: //项目列表
                loadRootFragment(R.id.Constants_Fragment, MainFragment.newInstance(0,1));
                break;
            case Constants.fragment_template: //项目模板
                loadRootFragment(R.id.Constants_Fragment, ProjectTemplate.newInstance());
                break;
            case Constants.fragment_Dealer: //经销商列表
                loadRootFragment(R.id.Constants_Fragment, DealerFragment.Instance(getIntent().getStringExtra("Template_id")));
                break;
            case Constants.fragment_webview: //网页
                loadRootFragment(R.id.Constants_Fragment, Fragment_WebView.Instance(getIntent().getStringExtra("url")));
                break;
            case Constants.fragment_search: //搜索页
                loadRootFragment(R.id.Constants_Fragment, SearchFragment.instance(getIntent().getIntExtra("type",0)));
                break;
            case Constants.fragment_umeng: //消息页
                loadRootFragment(R.id.Constants_Fragment, Fragment_Umeng.newInstance());
                break;
            case Constants.fragment_uploadOptions: //上传设置
                loadRootFragment(R.id.Constants_Fragment, UploadOptions.newInstance());
                break;

        }
    }

    @Override
    public void initData() {

    }

    //从  supportfragment 栈顶显示栈底某个fragment，并传递一个bundle
    public void jumpToSupportFragment(Class clazz, Bundle bundle) {
        getSupportFragmentManager().popBackStackImmediate(clazz.getName(), 0);
        BaseFragment fragment = (BaseFragment) findFragment(clazz);
        fragment.recieveData(bundle);
    }

    //登录成功回退到我的页面
    public void onLoginSuccessResult(int result) {
        setResult(result);
        finish();
    }

}
