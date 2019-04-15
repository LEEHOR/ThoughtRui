package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.List;
import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface Fragment_action_plan_pre_1_c {
    interface View extends BaseContract.View {
        void getProjectNameSuccess(List<ProjectsDB> projectsDBList);

        void getProjectNameFailure(String fail);

        void  getSubjectListSuccess(SubjectListBean subjectListBean);

        void  getSubjectListFailure(String failure,int code);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProjectName(String user_sessionId);

        void getProjectNameSuccess(List<ProjectsDB> projectsDBList);

        void getProjectNameFailure(String fail);

        void  getSubjectList(Map<String,Object> map);

        void  getSubjectListSuccess(SubjectListBean subjectListBean);

        void  getSubjectListFailure(String failure,int code);
    }

    interface Model extends BaseContract.Model {
        void getProjectName(String user_sessionId);

        void  getSubjectList(Map<String,Object> map);
    }
}
