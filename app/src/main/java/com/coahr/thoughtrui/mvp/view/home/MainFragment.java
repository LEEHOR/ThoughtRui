package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;

import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Main;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.presenter.MyMainFragmentP;
import com.coahr.thoughtrui.mvp.view.home.adapter.MainFragmentViewPageAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:42
 * 项目列表
 */
public class MainFragment extends BaseFragment<MyMainFragmentC.Presenter> implements MyMainFragmentC.View {
    @Inject
    MyMainFragmentP p;
    @BindView(R.id.home_tab)
    TabLayout home_tab;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    @BindView(R.id.ed_search)
    EditText tv_search;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    private MainFragmentViewPageAdapter pageAdapter;



    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MyMainFragmentC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mainfragment;
    }

    @Override
    public void initView() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToProject();
            }
        });
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToMassage();
            }
        });

    }

    @Override
    public void initData() {
        pageAdapter = new MainFragmentViewPageAdapter(getFragmentManager());
        viewPager.setAdapter(pageAdapter);
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void JumpToMassage(){
        Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_main);
        intent.putExtra("to", Constants.fragment_umeng);
        startActivity(intent);
    }

    /**
     * 跳转到搜索页

     */
    private void JumpToProject() {
        if (haslogin()) {
            Intent intent = new Intent(getActivity(), ConstantsActivity.class);
            intent.putExtra("from", Constants.MyTabFragmentCode);
            intent.putExtra("type", 1);
            intent.putExtra("to", Constants.fragment_search);
            startActivity(intent);
        } else {
            ToastUtils.showLong("请登录后再操作");
        }
    }
}
