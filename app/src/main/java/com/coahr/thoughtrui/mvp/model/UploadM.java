package com.coahr.thoughtrui.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;

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
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class UploadM extends BaseModel<UploadC.Presenter> implements UploadC.Model {
    private PutObjectRequest put;
    private int update;
    private int update1;
    private List<String> picList = new ArrayList<>();
    private ExecutorService cachedThreadPool;
    private ExecutorService fixedThreadPool;

    @Inject
    public UploadM() {
        super();
    }

    List<OSSAsyncTask> ossAsyncTaskList = new ArrayList<>();
    //上传成功的个数
    private int UpLoadSuccessCount = 0;
    //上传失败的个数
    private int UpLoadFailureCount = 0;
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    @Override
    public void getProjectList(String sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            UsersDB usersDB = usersDBS.get(0);
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id =? and puploadstatus =? and downloadtime!=? and isdeletes=? and iscomplete=?", String.valueOf(usersDB.getId()), String.valueOf(0), String.valueOf(-1), String.valueOf(0), String.valueOf(0));
            if (projectsDBS != null && projectsDBS.size() > 0) {
                getPresenter().getProjectListSuccess(projectsDBS);
            } else {
                getPresenter().getProjectListFailure("没有可以上传的项目");
            }
        }
    }


    @Override
    public void getSubjectList(List<ProjectsDB> projectsDBS, int position) {
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=? and iscomplete=? and suploadstatus=?", String.valueOf(projectsDBS.get(position).getId()), String.valueOf(1), String.valueOf(0));
        if (subjectsDBS != null && subjectsDBS.size() > 0) {
            getPresenter().getSubjectListSuccess(subjectsDBS, projectsDBS, position);
        } else {
            getPresenter().getSubjectListFailure("当前项目下没有可以上传的题目", projectsDBS, position);
        }

    }

    @Override
    public void UpLoadFileList(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDBS.get(project_position).getPid() + "/" + subjectsDBList.get(subject_position).getNumber() + "_" + subjectsDBList.get(subject_position).getHt_id());
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
            }
            getPresenter().getUpLoadFileListSuccess(fileList, subjectsDBList, projectsDBS, project_position, subject_position);
        } else {
            getPresenter().getUpLoadFileListFailure("当前题目下没有可以上传的数据");
        }
    }

    @Override
    public void startUpLoad(OSSClient ossClient, List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {

        int picUp = 0;
        picList.clear();

        ossAsyncTaskList.clear();
        UpLoadSuccessCount = 0;
        UpLoadFailureCount = 0;
        int CountSize = 0;
        String audioPath = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toLowerCase().endsWith("png") || list.get(i).toLowerCase().endsWith("jpeg")
                        || list.get(i).toLowerCase().endsWith("jpg") || list.get(i).toLowerCase().endsWith("gif")) {
                    CountSize++;
                    picList.add(list.get(i));
                } else if (list.get(i).toLowerCase().endsWith("wav") || list.get(i).toLowerCase().endsWith("amr")
                        || list.get(i).toLowerCase().endsWith("aac")) {
                    CountSize++;
                    audioPath = list.get(i);
                }
            }

            if (audioPath != null) {
                OSSAsyncTask ossAsyncTask = asyncPutImage(ossClient,
                        audioPath, CountSize, subjectsDBList, projectsDBS, project_position, subject_position, list, 1, 0, null);
                if (ossAsyncTask != null) {
                    ossAsyncTaskList.add(ossAsyncTask);
                }
            }

            if (picList != null && picList.size() > 0) {
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
                for (int i = 0; i < picList.size(); i++) {
                    int finalI = i;
                    int finalCountSize = CountSize;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            OSSAsyncTask ossAsyncTask = asyncPutImage(ossClient,
                                    picList.get(finalI), finalCountSize, subjectsDBList, projectsDBS, project_position, subject_position, list, 2, finalI + 1, picList);
                            if (ossAsyncTask != null) {
                                ossAsyncTaskList.add(ossAsyncTask);
                            }
                        }
                    });

                }

            }

            if (audioPath == null && (picList == null || picList.size() == 0)) {
                getPresenter().Pic_Compulsory(list, subjectsDBList, projectsDBS, project_position, subject_position);
            }

        }

    }

    @Override
    public void CallBackServer(Map<String, Object> map, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<UpLoadCallBack>(getApiService().upLoadCallBack(map)))
                .subscribeWith(new SimpleDisposableSubscriber<UpLoadCallBack>() {
                    @Override
                    public void _onNext(UpLoadCallBack upLoadCallBack) {
                        if (getPresenter() != null) {
                            if (upLoadCallBack.getResult() == 1) {
                                getPresenter().CallBackServerSuccess(subjectsDBList, projectsDBS, project_position, subject_position);
                            } else {
                                getPresenter().CallBackServerFailure(subjectsDBList, projectsDBS, project_position, subject_position);
                            }
                        }
                    }
                }));

        //getPresenter().CallBackServerSuccess(subjectsDBList,projectsDBS,project_position,subject_position);
    }

    @Override
    public void UpDataDb(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, boolean success) {
        //更新题目状态
        if (success) {  //是否上传和回调到后台成功
            SubjectsDB subjectsDB1 = new SubjectsDB();
            subjectsDB1.setsUploadStatus(1);
            subjectsDB1.setCensor(1);
            update = subjectsDB1.update(subjectsDBList.get(subject_position).getId());
        }

        //更新项目状态
        if (subject_position == subjectsDBList.size() - 1) {
            List<SubjectsDB> subjects_up = projectsDBS.get(project_position).getSubjectsDBList();
            if (subjects_up != null && subjects_up.size() > 0) {
                int up_subject = 0;
                for (int i = 0; i < subjects_up.size(); i++) {
                    if (subjects_up.get(i).getsUploadStatus() == 1) {
                        up_subject++;
                    }
                }
                //题目是否都传完了
                if (up_subject == subjects_up.size()) {
                    ProjectsDB projectsDB1 = new ProjectsDB();
                    projectsDB1.setIsComplete(1);
                    projectsDB1.setpUploadStatus(1);
                    update1 = projectsDB1.update(projectsDBS.get(project_position).getId());
                }
            }
        }

        //回调
        if (update == 1 || update1 == 1) {
            if (getPresenter() != null) {
                getPresenter().UpDataDbSuccess(subjectsDBList, projectsDBS, project_position, subject_position);
            }
        } else {
            if (getPresenter() != null) {
                getPresenter().UpDataDbFailure(subjectsDBList, projectsDBS, project_position, subject_position);
            }
        }

    }


    /**
     * 上传
     *
     * @param oss
     * @param localFile 文件
     * @param count     总大小
     */
    public OSSAsyncTask asyncPutImage(OSSClient oss, String localFile, final int count, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, final List<String> list, int type, int upPic, List<String> picList) {
        if (localFile.equals("")) {
            return null;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            return null;
        }

        String object = null;
        String fileName = getName(localFile, "/");
        if (type == 1) {
            String audioName = getName(localFile, "/");
            object = projectsDBS.get(project_position).getPid() + "/audios/" + audioName;
        } else {
            String picName = getName(localFile, ".").toLowerCase().equals("png") ? subjectsDBList.get(subject_position).getNumber() + "_" + upPic + ".png"
                    : getName(localFile, ".").toLowerCase().equals("jpeg") ? subjectsDBList.get(subject_position).getNumber() + "_" + upPic + ".jpeg"
                    : getName(localFile, ".").toLowerCase().equals("jpg") ? subjectsDBList.get(subject_position).getNumber() + "_" + upPic + ".jpg"
                    : getName(localFile, ".").toLowerCase().equals("gif") ? subjectsDBList.get(subject_position).getNumber() + "_" + upPic + ".gif"
                    : subjectsDBList.get(subject_position).getNumber() + "_" + upPic + ".png";
            object = projectsDBS.get(project_position).getPid() + "/pictures/" + subjectsDBList.get(subject_position).getNumber() + "/" + picName;
        }
        PutObjectRequest put = new PutObjectRequest(Constants.bucket, object, localFile);

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                KLog.d("日志_1", currentSize,totalSize,"第" + subjectsDBList.get(subject_position).getNumber() + "题\n" + fileName);
                        getPresenter().StartUiProgressSuccess((int) currentSize, (int) totalSize, "第" + subjectsDBList.get(subject_position).getNumber() + "题\n" + fileName);

            }
        });
        put.setCRC64(OSSRequest.CRC64Config.YES);
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                UpLoadSuccessCount++;
                if (getPresenter() != null) {
                    getPresenter().UploadCallBack(list, subjectsDBList, projectsDBS, picList, project_position, subject_position,
                            UpLoadSuccessCount, UpLoadFailureCount, count);
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
                if (getPresenter() != null) {
                    getPresenter().UploadCallBack(list, subjectsDBList, projectsDBS, picList, project_position, subject_position,
                            UpLoadSuccessCount, UpLoadFailureCount, count);
                }

            }
        });
        if (task != null) {
            task.waitUntilFinished();
            return task;
        }
        return null;
    }

    /**
     * 获取字符串
     *
     * @param path
     * @param flag
     * @return
     */
    public static String getName(String path, String flag) {
        int i = path.lastIndexOf(flag);
        String substring_name = path.substring(i + 1);
        return substring_name;
    }
}
