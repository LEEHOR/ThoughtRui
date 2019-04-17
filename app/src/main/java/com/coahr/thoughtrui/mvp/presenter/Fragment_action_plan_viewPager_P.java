package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_viewPager_c;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Fragment_Action_plan_pre_1_M;
import com.coahr.thoughtrui.mvp.model.Fragment_action_plan_viewPager_M;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_1;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_action_plan_viewPager;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class Fragment_action_plan_viewPager_P extends BasePresenter<Fragment_action_plan_viewPager_c.View, Fragment_action_plan_viewPager_c.Model> implements Fragment_action_plan_viewPager_c.Presenter {
    @Inject
    public Fragment_action_plan_viewPager_P(Fragment_action_plan_viewPager mview, Fragment_action_plan_viewPager_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getPlanList(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getPlanList(map);
        }
    }

    @Override
    public void getPlanListSuccess(ReportList reportList) {
        if (getView() != null) {
            getView().getPlanListSuccess(reportList);
        }
    }

    @Override
    public void getPlanListFailure(String failure) {
        if (getView() != null) {
            getView().getPlanListFailure(failure);
        }
    }
}
