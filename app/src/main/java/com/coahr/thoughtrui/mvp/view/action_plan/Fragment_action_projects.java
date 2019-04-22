package com.coahr.thoughtrui.mvp.view.action_plan;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_report;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.view.action_plan.Adapter.plan_ViewPagerAdapter;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.android.material.tabs.TabLayout;
import com.landptf.view.ButtonM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/11
 * 描述：行动计划提报首页
 */
public class Fragment_action_projects extends BaseFragment {
    @BindView(R.id.plan_tittle)
    MyTittleBar planTittle;
    @BindView(R.id.plan_top_line)
    View planTopLine;
    @BindView(R.id.plan_action_pre)
    ButtonM planActionPre;
    @BindView(R.id.plan_bottom_parts)
    RelativeLayout planBottomParts;
    @BindView(R.id.plan_tab)
    TabLayout planTab;
    @BindView(R.id.plan_viewPager)
    ViewPager planViewPager;
    private plan_ViewPagerAdapter plan_viewPagerAdapter;
    private boolean canReport;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fragment_action_projects newInstance() {
        return new Fragment_action_projects();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_projects;
    }

    @Override
    public void initView() {
        planActionPre.setFillet(true);
        planActionPre.setRadius(10);
        planActionPre.setBackColor(getResources().getColor(R.color.material_blue_700));
        planActionPre.setTextColor(getResources().getColor(R.color.colorWhite));
        planActionPre.setShape(GradientDrawable.RECTANGLE);
        planTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
        plan_viewPagerAdapter = new plan_ViewPagerAdapter(getChildFragmentManager());
        planViewPager.setAdapter(plan_viewPagerAdapter);
        planTab.setupWithViewPager(planViewPager);
        planViewPager.setCurrentItem(0);

    }

    @Override
    public void initData() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @OnClick(R.id.plan_action_pre)  //填写行动计划报表
    public void onViewClicked() {
        if (canReport) {
            start(Fragment_Action_plan_presentation_1.newInstance(null,1));
        } else {
            ToastUtils.showLong(getResources().getString(R.string.phrases_30));
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event_report(EvenBus_report report) {
        canReport = report.isCanReport();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
