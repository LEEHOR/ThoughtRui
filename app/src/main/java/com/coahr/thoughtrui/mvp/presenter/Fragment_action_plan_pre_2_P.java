package com.coahr.thoughtrui.mvp.presenter;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_2_c;
import com.coahr.thoughtrui.mvp.model.Bean.SubmitReport;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Fragment_Action_plan_pre_1_M;
import com.coahr.thoughtrui.mvp.model.Fragment_Action_plan_pre_2_M;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_1;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_2;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class Fragment_action_plan_pre_2_P extends BasePresenter<Fragment_action_plan_pre_2_c.View, Fragment_action_plan_pre_2_c.Model> implements Fragment_action_plan_pre_2_c.Presenter {
    @Inject
    public Fragment_action_plan_pre_2_P(Fragment_Action_plan_presentation_2 mview, Fragment_Action_plan_pre_2_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getAfterPic(OSS ossClient, String projectId, String levelId) {
        if (mModle != null) {
            mModle.getAfterPic(ossClient, projectId, levelId);
        }
    }

    @Override
    public void getAfterPicSuccess(List<OSSObjectSummary> ossObjectSummaries) {
        if (getView() != null) {
            getView().getAfterPicSuccess(ossObjectSummaries);
        }
    }

    @Override
    public void getAfterPicFailure(String failure) {
        if (getView() != null) {
            getView().getAfterPicFailure(failure);
        }
    }

    @Override
    public void getAfterPicUrl(OSS oss, List<String> picKeyList) {
        if (mModle != null) {
            mModle.getAfterPicUrl(oss, picKeyList);
        }
    }

    @Override
    public void getAfterPicUrlSuccess(List<String> picUrlList) {
        if (getView() != null) {
            getView().getAfterPicUrlSuccess(picUrlList);
        }
    }

    @Override
    public void getAfterPicUrlFailure(String failure) {
        if (getView() != null) {
            getView().getAfterPicFailure(failure);
        }
    }

    @Override
    public void putImagesUpload(OSS oss, List<String> beforeImage, List<String> afterImage, String projectId, String levelId) {
        if (mModle != null) {
            mModle.putImagesUpload(oss, beforeImage, afterImage, projectId, levelId);
        }
    }

    @Override
    public void putUploadImagesCallBack(int TotalSize, int successSize, int failureSize) {
        if (getView() != null) {
            getView().putUploadImagesCallBack(TotalSize, successSize, failureSize);
        }
    }

    @Override
    public void SubmitReport(Map<String, Object> map) {
        if (mModle != null) {
            mModle.SubmitReport(map);
        }
    }

    @Override
    public void SubmitReportSuccess(SubmitReport submitReport) {
        if (getView() != null) {
            getView().SubmitReportSuccess(submitReport);
        }
    }

    @Override
    public void SubmitReportFailure(String failure) {
        if (getView() != null) {
            getView().SubmitReportFailure(failure);
        }
    }

    @Override
    public void putUploadProgress(long currentSize, long totalSize, String FileName) {
        if (getView() != null) {
            getView().putUploadProgress(currentSize, totalSize, FileName);
        }
    }
}
