package com.coahr.thoughtrui.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class UploadM extends BaseModel<UploadC.Presenter> implements UploadC.Model {
    @Inject
    public UploadM() {
        super();
    }
    List<OSSAsyncTask> ossAsyncTaskList=new ArrayList<>();
    //上传成功的个数
    private int UpLoadSuccessCount=0;
    //上传失败的个数
    private int UpLoadFailureCount=0;
    @Override
    public void getSTS(String utl) {
        OSSAuthCredentialsProvider ossAuthCredentialsProvider = new OSSAuthCredentialsProvider(utl);
        if (ossAuthCredentialsProvider != null) {
            getPresenter().getSTSSuccess(ossAuthCredentialsProvider);
        } else {
            getPresenter().getSTSFailure("Ak鉴权失败");
        }
    }

    @Override
    public void getOSS(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        OSS ossClient = new OSSClient(context, endpoint, credentialProvider, conf);
        if (ossClient != null) {
            getPresenter().getOSSSuccess(ossClient);
        } else {
            getPresenter().getOSSFailure("获取阿里云上传实例失败");
        }
    }

    @Override
    public void getProjectList(String sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
        if (usersDBS != null && usersDBS.size()>0) {
            UsersDB usersDB = usersDBS.get(0);
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id =? and puploadstatus = ? and downloadtime!=? and isdeletes=? and iscomplete=?", String.valueOf(usersDB.getId()), String.valueOf(0),String.valueOf(-1),String.valueOf(0),String.valueOf(0));
            if (projectsDBS != null && projectsDBS.size()>0) {
                getPresenter().getProjectListSuccess(projectsDBS);
            } else {
                getPresenter().getProjectListFailure("获取项目列表失败或为空");
            }
        }
    }

    @Override
    public void getSubjectList(ProjectsDB projectsDBList) {
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=? and iscomplete=? and suploadstatus=?", String.valueOf(projectsDBList.getId()), String.valueOf(0), String.valueOf(0));
        if (subjectsDBS != null && subjectsDBS.size()>0) {
            getPresenter().getSubjectListSuccess(subjectsDBS,projectsDBList);
        } else {
            getPresenter().getSubjectListFailure("获取题目列表出错");
        }

    }

    @Override
    public void UpLoadFileList(ProjectsDB projectsDB, SubjectsDB subjectsDB, Activity activity) {
        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB.getPid() + "/" + subjectsDB.getNumber() + "_" + subjectsDB.getHt_id());
        if (fileList != null && fileList.size()>0) {
            getPresenter().getUoLoadFileListSuccess(fileList,projectsDB,subjectsDB);
        } else {
            getPresenter().getUpLoadFileListFailure("获取题目答案失败");
        }

    }

    @Override
    public void StartUpLoad(OSS oss,List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        ossAsyncTaskList.clear();
        if (list != null && list.size()>0) {
            for (int i = 0; i <list.size() ; i++) {
                OSSAsyncTask ossAsyncTask = asyncPutImage(oss, projectsDB.getPid(), getName(list.get(i), "/"),
                        list.get(i), list.size(), i,projectsDB,subjectsDB,list);
                if (ossAsyncTask != null) {
                    ossAsyncTaskList.add(ossAsyncTask);
                }
            }
        }
    }

    @Override
    public void CallBack(Map<String, Object> map, final ProjectsDB projectsDB, final SubjectsDB subjectsDB) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<UpLoadCallBack>(getApiService().upLoadCallBack(map)))
                .subscribeWith(new SimpleDisposableSubscriber<UpLoadCallBack>() {
            @Override
            public void _onNext(UpLoadCallBack upLoadCallBack) {
                if (getPresenter() != null) {
                    if (upLoadCallBack.getResult()==1) {
                        getPresenter().CallBackSuccess(projectsDB,subjectsDB);
                    }else {
                        getPresenter().CallBackFailure(projectsDB,subjectsDB);
                    }
                }
            }
        }));
    }

    /**
     * 上传
     * @param oss
     * @param bucket
     * 路径
     * @param object
     * 名字
     * @param localFile
     * 文件
     * @param count
     * 总大小
     * @param position
     * 当前位置
     */
    public OSSAsyncTask asyncPutImage(OSS oss, String bucket, String object, String localFile, final int count, int position, final ProjectsDB projectsDB, final SubjectsDB subjectsDB, final List<String> list) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return null;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return null;
        }
        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);
    /*    if (ApiContact.callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", ApiContact.callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }*/
  /*      // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
            }
        });*/

        OSSLog.logDebug(" asyncPutObject ");
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                UpLoadSuccessCount++;
                if (UpLoadSuccessCount == count){
                    //每题的回调
                    if (getPresenter() != null) {
                        UpLoadSuccessCount=0;
                        UpLoadFailureCount=0;
                        getPresenter().StartUpLoadSuccess(projectsDB, subjectsDB,list);
                    }
                } else {
                    if (getPresenter() != null) {
                        UpLoadSuccessCount=0;
                        UpLoadFailureCount=0;
                        getPresenter().StartUpLoadFailure(projectsDB,subjectsDB);
                    }
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
                    UpLoadFailureCount++;
                if (UpLoadSuccessCount+UpLoadFailureCount==count) {
                    if (getPresenter() != null) {
                        UpLoadSuccessCount=0;
                        UpLoadFailureCount=0;
                        getPresenter().StartUpLoadFailure(projectsDB,subjectsDB);
                    }
                }

            }
        });
        if (task != null) {
            return  task;
        }
        return null;
    }

    /**
     * 获取字符串
     * @param path
     * @param flag
     * @return
     */
    public static String getName(String path,String flag){
        int i = path.lastIndexOf(flag);
        String substring_name = path.substring(i+1);
        return substring_name;
    }
}
