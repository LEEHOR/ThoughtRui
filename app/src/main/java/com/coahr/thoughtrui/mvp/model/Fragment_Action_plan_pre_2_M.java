package com.coahr.thoughtrui.mvp.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_2_c;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.model.Bean.SubmitReport;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class Fragment_Action_plan_pre_2_M extends BaseModel<Fragment_action_plan_pre_2_c.Presenter> implements Fragment_action_plan_pre_2_c.Model {
    @Inject
    public Fragment_Action_plan_pre_2_M() {
        super();
    }

    private int successCount, failureCount;
    private ArrayList<String> PicUrlList = new ArrayList<>();


    @Override
    public void getAfterPic(OSS ossClient, String projectId, String levelId) {
        ListObjectsRequest listObjects = new ListObjectsRequest(Constants.bucket);
        listObjects.setPrefix(String.format(BaseApplication.mContext.getResources().getString(R.string.plan_2_12),projectId,levelId));
        ossClient.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {

            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                for (int i = 0; i < result.getObjectSummaries().size(); i++) {
                    KLog.d("图片", result.getObjectSummaries().get(i).getKey());
                }
                if (getPresenter() != null) {
                    getPresenter().getAfterPicSuccess(result.getObjectSummaries());
                }
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {
                if (getPresenter() != null) {
                    getPresenter().getAfterPicFailure(clientException.getMessage());
                }
            }
        });
    }

    @Override
    public void getAfterPicUrl(OSS oss, List<String> picKeyList) {
        if (picKeyList != null && picKeyList.size() > 0) {
            PicUrlList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < picKeyList.size(); i++) {
                        try {
                            String url = oss.presignConstrainedObjectURL(Constants.bucket, picKeyList.get(i), 10 * 60);
                            PicUrlList.add(url);
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                    if (getPresenter() != null) {
                        getPresenter().getAfterPicUrlSuccess(PicUrlList);
                    }
                }
            }).start();
        } else {
            if (getPresenter() != null) {
                getPresenter().getAfterPicUrlFailure("");
            }
        }

    }

    @Override
    public void putImagesUpload(OSS oss, List<String> beforeImage, List<String> afterImage, String projectId, String levelId) {
        successCount = 0;
        failureCount = 0;
        if (beforeImage != null && beforeImage.size() > 0) {
            for (int i = 0; i < beforeImage.size(); i++) {
                putImages(oss, beforeImage.get(i), projectId, levelId, beforeImage.size() + afterImage.size(), 1);
                KLog.d("before", beforeImage.get(i));
            }
        }

        if (afterImage != null && afterImage.size() > 0) {
            for (int i = 0; i < afterImage.size(); i++) {
                putImages(oss, afterImage.get(i), projectId, levelId, beforeImage.size() + afterImage.size(), 2);
                KLog.d("after", afterImage.get(i));
            }
        }
        if (beforeImage.size()+afterImage.size()==0){
            if (getPresenter() != null) {
                getPresenter().putUploadProgress(0, 0, "");
            }
        }
    }

    @Override
    public void SubmitReport(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<SubmitReport>(getApiService().submitReport(map)))
                .subscribeWith(new SimpleDisposableSubscriber<SubmitReport>() {
                    @Override
                    public void _onNext(SubmitReport submitReport) {
                        if (getPresenter() != null) {
                            if (submitReport.getResult()==1) {
                                getPresenter().SubmitReportSuccess(submitReport);
                            }else {
                                getPresenter().SubmitReportFailure(submitReport.getMsg());
                            }
                        }
                    }
                }));
    }

    /**
     * 上传
     *
     * @param oss
     * @param picPath
     * @param projectId
     * @param levelId
     * @param TotalSize
     */
    private void putImages(OSS oss, String picPath, String projectId, String levelId, int TotalSize, int type) {

        String object = String.format("%1$s/%2$s/%3$s/%4$s/%5$s", "report", projectId, levelId, type==1?"before":type==2?"after":"before", FileIOUtils.getE(picPath, "/"));
        KLog.d("object", object);
        PutObjectRequest put = new PutObjectRequest(Constants.bucket, object, picPath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if (getPresenter() != null) {
                    getPresenter().putUploadProgress(currentSize, totalSize, FileIOUtils.getE(picPath, "/"));
                }
            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                successCount++;
                if (getPresenter() != null) {
                    getPresenter().putUploadImagesCallBack(TotalSize, successCount, failureCount);
                }

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                failureCount++;
                if (getPresenter() != null) {
                    getPresenter().putUploadImagesCallBack(TotalSize, successCount, failureCount);
                }
            }
        });

    }
}
