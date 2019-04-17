package com.coahr.thoughtrui.mvp.presenter;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Fragment_Action_plan_pre_1_M;
import com.coahr.thoughtrui.mvp.model.ProjectDealer_M;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_1;
import com.coahr.thoughtrui.mvp.view.home.DealerFragment;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class Fragment_action_plan_pre_1_P extends BasePresenter<Fragment_action_plan_pre_1_c.View, Fragment_action_plan_pre_1_c.Model> implements Fragment_action_plan_pre_1_c.Presenter {
    @Inject
    public Fragment_action_plan_pre_1_P(Fragment_Action_plan_presentation_1 mview, Fragment_Action_plan_pre_1_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getProjectName(String user_sessionId) {
        if (mModle != null) {
            mModle.getProjectName(user_sessionId);
        }
    }

    @Override
    public void getProjectNameSuccess(List<ProjectsDB> projectsDBList) {
        if (getView() != null) {
            getView().getProjectNameSuccess(projectsDBList);
        }
    }

    @Override
    public void getProjectNameFailure(String fail) {
        if (getView() != null) {
            getView().getProjectNameFailure(fail);
        }
    }

    @Override
    public void getSubjectList(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getSubjectList(map);
        }
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (getView() != null) {
            getView().getSubjectListSuccess(subjectListBean);
        }
    }

    @Override
    public void getSubjectListFailure(String failure, int code) {
        if (getView() != null) {
            getView().getSubjectListFailure(failure,code);
        }
    }

    @Override
    public void getBeforePic(OSS ossClient, String projectId, String levelId) {
        if (mModle != null) {
            mModle.getBeforePic(ossClient, projectId, levelId);
        }
    }

    @Override
    public void getBeforePicSuccess(List<OSSObjectSummary> ossObjectSummaries) {
        if (getView() != null) {
            getView().getBeforePicSuccess(ossObjectSummaries);
        }
    }

    @Override
    public void getBeforePicFailure(String failure) {
        if (getView() != null) {
            getView().getBeforePicFailure(failure);
        }
    }

    @Override
    public void getBeforePicUrl(OSS oss, List<String> picKeyList) {
        if (mModle != null) {
            mModle.getBeforePicUrl(oss,picKeyList);
        }
    }

    @Override
    public void getBeforePicUrlSuccess(List<String> picUrlList) {
        if (getView() != null) {
            getView().getBeforePicUrlSuccess(picUrlList);
        }
    }

    @Override
    public void getBeforePicUrlFailure(String failure) {
        if (getView() != null) {
            getView().getBeforePicUrlFailure(failure);
        }
    }

    @Override
    public void putBeforeUpload(OSS oss, List<String> picUpload,String projectId,String LevelId) {
        if (mModle != null) {
            mModle.putBeforeUpload(oss,picUpload,projectId,LevelId);
        }
    }

    @Override
    public void putBeforeUploadCallBack(int TotalSize, int successSize, int failureSize) {
        if (getView() != null) {
            getView().putBeforeUploadCallBack(TotalSize, successSize, failureSize);
        }
    }
}
