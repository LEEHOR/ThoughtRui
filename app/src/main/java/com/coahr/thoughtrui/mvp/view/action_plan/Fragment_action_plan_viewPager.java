package com.coahr.thoughtrui.mvp.view.action_plan;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_viewPager_c;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_viewPager_P;

import javax.inject.Inject;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/15
 * 描述：
 */
public class Fragment_action_plan_viewPager extends BaseChildFragment<Fragment_action_plan_viewPager_c.Presenter> implements Fragment_action_plan_viewPager_c.View {
    @Inject
    Fragment_action_plan_viewPager_P p;
    @BindView(R.id.plan_viewpager_recycler)
    RecyclerView planViewpagerRecycler;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_plan_viewpager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void getPlanListSuccess(ReportList reportList) {

    }

    @Override
    public void getPlanListFailure(String failure) {

    }
}
