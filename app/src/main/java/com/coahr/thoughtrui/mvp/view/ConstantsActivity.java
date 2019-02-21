package com.coahr.thoughtrui.mvp.view;

import android.content.IntentFilter;
import android.os.Bundle;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_LoginSuccess;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;
import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;
import com.coahr.thoughtrui.mvp.view.SubjectList.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.AliyunHotReceiver;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.isRegister;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
                loadRootFragment(R.id.Constants_Fragment, ProjectDetailFragment.newInstance(getIntent().getStringExtra("projectId")));
                break;
//            case Constants.startProjectFragment: //开始访问页
//                loadRootFragment(R.id.Constants_Fragment, StartProjectFragment.newInstance());
//                break;
            case Constants.fragment_topics: //题目列表
                loadRootFragment(R.id.Constants_Fragment,Fragment_Topics.newInstance());
                break;
            case Constants.fragment_ChangePass://修改密码
                loadRootFragment(R.id.Constants_Fragment,ChangePasswordFragment.newInstance());
                break;
            case Constants.fragment_AnnexViewPager: //项目附件
                loadRootFragment(R.id.Constants_Fragment,FragmentAnnexViewPager.newInstance());
                break;
            case Constants.fragment_review_list:  //审核列表
                loadRootFragment(R.id.Constants_Fragment,ReviewInfoList.newInstance(getIntent().getStringExtra("projectId"),getIntent().getStringExtra("sessionId"),getIntent().getIntExtra("type",0)));
                break;
            case Constants.fragment_feedback://帮助与反馈
                loadRootFragment(R.id.Constants_Fragment,Fragment_Feedback.newInstance());
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
