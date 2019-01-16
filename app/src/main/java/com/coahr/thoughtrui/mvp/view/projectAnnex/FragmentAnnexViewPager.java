package com.coahr.thoughtrui.mvp.view.projectAnnex;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.view.projectAnnex.adapter.AnnexViewPagerAdapter;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：项目附件ViewPager
 */
public class FragmentAnnexViewPager extends BaseFragment {
    @BindView(R.id.annex_tittle)
    MyTittleBar annex_tittle;
    @BindView(R.id.annex_tab)
    TabLayout annex_tab;
    @BindView(R.id.annex_viewpager)
    ViewPager annex_viewpager;
    private AnnexViewPagerAdapter annexViewPagerAdapter;

    public static FragmentAnnexViewPager newInstance() {
        return new FragmentAnnexViewPager();
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_annex_viewpager;
    }

    @Override
    public void initView() {
        annex_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
            annexViewPagerAdapter = new AnnexViewPagerAdapter(getChildFragmentManager(),Constants.ht_ProjectId);
            annex_viewpager.setAdapter(annexViewPagerAdapter);
            annex_tab.setupWithViewPager(annex_viewpager);

    }

    @Override
    public void initData() {

    }
}
