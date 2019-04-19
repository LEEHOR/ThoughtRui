package com.coahr.thoughtrui.mvp.constract;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.SubmitReport;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.List;
import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface Fragment_action_plan_pre_2_c {
    interface View extends BaseContract.View {

        void getAfterPicSuccess(List<OSSObjectSummary> ossObjectSummaries);

        void getAfterPicFailure(String failure);

        void getAfterPicUrlSuccess(List<String> picUrlList);

        void putUploadImagesCallBack(int TotalSize, int successSize, int failureSize);

        void SubmitReportSuccess(SubmitReport submitReport);

        void SubmitReportFailure(String failure);

        void putUploadProgress(long currentSize, long totalSize,String FileName);
    }

    interface Presenter extends BaseContract.Presenter {


        void getAfterPic(OSS ossClient, String projectId, String levelId);

        void getAfterPicSuccess(List<OSSObjectSummary> ossObjectSummaries);

        void getAfterPicFailure(String failure);

        void getAfterPicUrl(OSS oss, List<String> picKeyList);

        void getAfterPicUrlSuccess(List<String> picUrlList);

        void getAfterPicUrlFailure(String failure);

        void putImagesUpload(OSS oss, List<String> beforeImage,List<String> afterImage,String projectId, String levelId,int type);

        void putUploadImagesCallBack(int TotalSize, int successSize, int failureSize);

        void SubmitReport(Map<String,Object> map);

        void SubmitReportSuccess(SubmitReport submitReport);

        void SubmitReportFailure(String failure);

        void putUploadProgress(long currentSize, long totalSize,String FileName);
    }

    interface Model extends BaseContract.Model {


        void getAfterPic(OSS ossClient, String projectId, String levelId);

        void getAfterPicUrl(OSS oss, List<String> picKeyList);

        void putImagesUpload(OSS oss, List<String> beforeImage,List<String> afterImage,String projectId, String levelId,int type);


        void SubmitReport(Map<String,Object> map);

    }
}
