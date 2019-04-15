package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
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
public class Fragment_Action_plan_pre_1_M extends BaseModel<Fragment_action_plan_pre_1_c.Presenter> implements Fragment_action_plan_pre_1_c.Model {
    @Inject
    public Fragment_Action_plan_pre_1_M() {
        super();
    }

    @Override
    public void getProjectName(String user_sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", user_sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                getPresenter().getProjectNameSuccess(projectsDBSList);
            } else {
                getPresenter().getProjectNameFailure(BaseApplication.mContext.getResources().getString(R.string.phrases_9));
            }
        } else {
            getPresenter().getProjectNameFailure(BaseApplication.mContext.getResources().getString(R.string.phrases_9));
        }
    }

    @Override
    public void getSubjectList(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<SubjectListBean>(getApiService().getSubjectList(map)))
                .subscribeWith(new SimpleDisposableSubscriber<SubjectListBean>() {
                    @Override
                    public void _onNext(SubjectListBean SubjectListBean) {
                        if (getPresenter() != null) {
                            if (SubjectListBean.getResult() == 1) {
                                getPresenter().getSubjectListSuccess(SubjectListBean);
                            } else {
                                getPresenter().getSubjectListFailure(SubjectListBean.getMsg(), SubjectListBean.getResult());
                            }
                        }
                    }
                }));
    }
}
