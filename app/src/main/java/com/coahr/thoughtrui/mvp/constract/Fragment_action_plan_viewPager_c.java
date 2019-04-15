package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.List;
import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface Fragment_action_plan_viewPager_c {
    interface View extends BaseContract.View {
        void getPlanListSuccess(ReportList reportList);

        void getPlanListFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {
        void getPlanList(Map<String, Object> map);

        void getPlanListSuccess(ReportList reportList);

        void getPlanListFailure(String failure);
    }

    interface Model extends BaseContract.Model {
        void getPlanList(Map<String, Object> map);
    }
}
