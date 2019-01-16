package com.coahr.thoughtrui.mvp.view;

import android.os.Bundle;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.SubjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;

import org.greenrobot.eventbus.EventBus;

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
                loadRootFragment(R.id.Constants_Fragment, LoginFragment.newInstance(getIntent().getIntExtra("type", 0),getIntent().getIntExtra("from",-1)));
                break;
            case Constants.ProjectDetailFragmentCode://项目详情页
                loadRootFragment(R.id.Constants_Fragment, ProjectDetailFragment.newInstance());
                break;
//            case Constants.startProjectFragment: //开始访问页
//                loadRootFragment(R.id.Constants_Fragment, StartProjectFragment.newInstance());
//                break;
            case Constants.fragment_topics:
                loadRootFragment(R.id.Constants_Fragment,Fragment_Topics.newInstance());
                break;
            case Constants.fragment_ChangePass:
                loadRootFragment(R.id.Constants_Fragment,ChangePasswordFragment.newInstance());
                break;
            case Constants.fragment_AnnexViewPager:
                loadRootFragment(R.id.Constants_Fragment,FragmentAnnexViewPager.newInstance());
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
