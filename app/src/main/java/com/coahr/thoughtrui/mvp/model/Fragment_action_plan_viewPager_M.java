package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_viewPager_c;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class Fragment_action_plan_viewPager_M extends BaseModel<Fragment_action_plan_viewPager_c.Presenter> implements Fragment_action_plan_viewPager_c.Model {
    @Inject
    public Fragment_action_plan_viewPager_M() {
        super();
    }

    @Override
    public void getPlanList(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<ReportList>(getApiService().getReportList(map)))
                .subscribeWith(new SimpleDisposableSubscriber<ReportList>() {
                    @Override
                    public void _onNext(ReportList reportList) {
                        if (getPresenter() != null) {
                            if (reportList.getResult() == 1) {
                                getPresenter().getPlanListSuccess(reportList);
                            } else {
                                getPresenter().getPlanListFailure(reportList.getMsg());
                            }
                        }
                    }
                }));
    }

}
