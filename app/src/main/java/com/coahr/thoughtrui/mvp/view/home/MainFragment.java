package com.coahr.thoughtrui.mvp.view.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.presenter.MyMainFragmentP;
import com.coahr.thoughtrui.mvp.view.UMPush.Fragment_Umeng;
import com.coahr.thoughtrui.mvp.view.home.adapter.MainFragmentViewPageAdapter;
import com.coahr.thoughtrui.mvp.view.search.SearchFragment;
import com.google.android.material.tabs.TabLayout;

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private MainFragmentViewPageAdapter pageAdapter;
    private int type;
    private int page;


    public static MainFragment newInstance(int page, int type) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        bundle.putInt("type", type);
        mainFragment.setArguments(bundle);
        return mainFragment;
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
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            page = getArguments().getInt("page");
        }
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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        pageAdapter = new MainFragmentViewPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(pageAdapter);
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        if (type == 2) {
            viewPager.setCurrentItem(page);
        } else {
            viewPager.setCurrentItem(0);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转到消息中心
     */
    private void JumpToMassage() {
        start(Fragment_Umeng.newInstance());
        // Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        // intent.putExtra("from", Constants.fragment_main);
        // intent.putExtra("to", Constants.fragment_umeng);
        // startActivity(intent);
    }

    /**
     * 跳转到搜索页
     */
    private void JumpToProject() {
        if (haslogin()) {
            start(SearchFragment.instance(1));
            //  Intent intent = new Intent(getActivity(), ConstantsActivity.class);
            // intent.putExtra("from", Constants.MyTabFragmentCode);
            //intent.putExtra("type", 1);
            // intent.putExtra("to", Constants.fragment_search);
            // startActivity(intent);
        } else {
            ToastUtils.showLong(getResources().getString(R.string.toast_10));
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
