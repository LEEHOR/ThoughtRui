package com.coahr.thoughtrui.mvp.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
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
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.recyclerview.widget.SortedList;

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

    private int successCount, failureCount;
    private ArrayList<String> PicUrlList = new ArrayList<>();

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

    @Override
    public void getProjectTemplates(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<Template_list>(getApiService().template(map)))
                .subscribeWith(new SimpleDisposableSubscriber<Template_list>() {
                    @Override
                    public void _onNext(Template_list template_list) {
                        if (getPresenter() != null) {
                            if (template_list.getStatus() == 1) {
                                getPresenter().getProjectTemplatesSuccess(template_list);
                            } else {
                                getPresenter().getProjectTemplateFailure(template_list.getStatus()+"");
                            }
                        }
                    }
                }));
    }

    @Override
    public void getOss(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<AliyunOss>(getApiService().getAliYunOss(map)))
                .subscribeWith(new SimpleDisposableSubscriber<AliyunOss>() {
                    @Override
                    public void _onNext(AliyunOss aliyunOss) {
                        if (getPresenter() != null) {
                            if (aliyunOss.getStatusCode() == 200) {
                                getPresenter().getOssSuccess(aliyunOss);
                            } else {
                                getPresenter().getOssFailure(aliyunOss.getStatusCode());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getBeforePic(OSS oss, String projectId, String levelId) {
        ListObjectsRequest listObjects = new ListObjectsRequest(Constants.bucket);
        listObjects.setPrefix(String.format(BaseApplication.mContext.getResources().getString(R.string.plan_1_12),projectId,levelId));
        oss.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {

                for (int i = 0; i < result.getObjectSummaries().size(); i++) {
                    KLog.d("图片", result.getObjectSummaries().get(i).getKey());
                }
                if (getPresenter() != null) {
                    getPresenter().getBeforePicSuccess(result.getObjectSummaries());
                }
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {
                KLog.d("图片2", clientException.getMessage(),request.getBucketName());
                if (getPresenter() != null) {
                    getPresenter().getBeforePicFailure(request.getBucketName());
                }
            }
        });
    }

    @Override
    public void getBeforePicUrl(OSS oss, List<String> picKeyList) {
        if (picKeyList != null && picKeyList.size() > 0) {
            PicUrlList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < picKeyList.size(); i++) {
                        try {
                            String url = oss.presignConstrainedObjectURL(Constants.bucket, picKeyList.get(i), 10 * 60);
                            KLog.d("地址",url);
                            PicUrlList.add(url);
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                    if (getPresenter() != null) {
                        getPresenter().getBeforePicUrlSuccess(PicUrlList);
                    }
                }
            }).start();

        } else {
            if (getPresenter() != null) {
                getPresenter().getBeforePicUrlFailure("");
            }
        }
    }

    @Override
    public void putBeforeUpload(OSS oss, List<String> picUpload, String projectId, String levelId) {
        if (picUpload != null && picUpload.size() > 0) {
            successCount = 0;
            failureCount = 0;
            for (int i = 0; i < picUpload.size(); i++) {
                putImages(oss, picUpload.get(i), projectId, levelId, picUpload.size());
            }
        }

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
    private void putImages(OSS oss, String picPath, String projectId, String levelId, int TotalSize) {

        String object = String.format("%1$s/%2$s/%3$s/%4$s/%5$s", "report", projectId, levelId, "before", FileIOUtils.getE(picPath, "/"));

        PutObjectRequest put = new PutObjectRequest(Constants.bucket, object, picPath);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                successCount++;
                if (getPresenter() != null) {
                    getPresenter().putBeforeUploadCallBack(TotalSize, successCount, failureCount);
                }

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                failureCount++;
                if (getPresenter() != null) {
                    getPresenter().putBeforeUploadCallBack(TotalSize, successCount, failureCount);
                }
            }
        });

    }
}
