package com.coahr.thoughtrui.mvp.constract;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
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

        void getSubjectListSuccess(SubjectListBean subjectListBean);

        void getSubjectListFailure(String failure, int code);

        void getBeforePicSuccess(List<OSSObjectSummary> ossObjectSummaries);

        void getBeforePicFailure(String failure);

        void getBeforePicUrlSuccess(List<String> picUrlList);

        void getBeforePicUrlFailure(String failure);

        void putBeforeUploadCallBack(int TotalSize,int successSize,int failureSize);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProjectName(String user_sessionId);

        void getProjectNameSuccess(List<ProjectsDB> projectsDBList);

        void getProjectNameFailure(String fail);

        void getSubjectList(Map<String, Object> map);

        void getSubjectListSuccess(SubjectListBean subjectListBean);

        void getSubjectListFailure(String failure, int code);

        void getBeforePic(OSS ossClient, String projectId, String levelId);

        void getBeforePicSuccess(List<OSSObjectSummary> ossObjectSummaries);

        void getBeforePicFailure(String failure);

        void getBeforePicUrl(OSS oss,List<String> picKeyList);

        void getBeforePicUrlSuccess(List<String> picUrlList);

        void getBeforePicUrlFailure(String failure);

        void putBeforeUpload(OSS oss,List<String> picUpload,String projectId,String levelId);

        void putBeforeUploadCallBack(int TotalSize,int successSize,int failureSize);
    }

    interface Model extends BaseContract.Model {

        void getProjectName(String user_sessionId);

        void getSubjectList(Map<String, Object> map);

        void getBeforePic(OSS oss, String projectId, String levelId);

        void getBeforePicUrl(OSS oss,List<String> picKeyList);

        void putBeforeUpload(OSS oss,List<String> picUpload,String projectId,String levelId);

    }
}
