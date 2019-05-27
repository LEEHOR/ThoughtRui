package com.coahr.thoughtrui.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.provider.SyncStateContract;
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
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.StoreSpaceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 23:04
 */
public class PagerFragment_aM extends BaseModel<PagerFragment_aC.Presenter> implements PagerFragment_aC.Model {

    private ClientConfiguration conf;
    private OSSClient ossClient;
    private int update1;
    private int update;
    private List<String> upList = new ArrayList<>();
    private ExecutorService fixedThreadPool;

    @Inject
    public PagerFragment_aM() {
        super();
    }

    private int uploadSuccess, uploadFailure;

    @Override
    public void getSubject(final String ht_ProjectId, Activity activity, int number, String ht_id) {
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "ht_id=?", ht_id);
        if (subjectsDBS != null && subjectsDBS.size() > 0) {
            // getImage(ht_ProjectId,activity,number,ht_id);
            //  getAnswer(ht_ProjectId,activity,number,ht_id);
            //  getAudio(ht_ProjectId,activity,number,ht_id);
            getPresenter().getSubjectSuccess(subjectsDBS.get(0));
        } else {
            getPresenter().getSubjectFailure("0");
            getPresenter().getImageFailure();
            getPresenter().getAnswerFailure();
        }

    }

    @Override
    public void getImage(final String ht_ProjectId, Activity activity, final int number, final String ht_id) {
        //获取当前题目下的图片
        List<String> picturesList = FileIOUtils.getPictures(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number + "_" + ht_id);
        if (picturesList != null) {
            getPresenter().getImageSuccess(picturesList);
        } else {
            getPresenter().getImageFailure();
        }

    }

    @Override
    public void getAnswer(final String ht_ProjectId, Activity activity, final int number, final String ht_id) {

        String s = SaveOrGetAnswers.readFromFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number + "_" + ht_id + "/" + "AnswerAndRemark.txt");
        if (s != null) {
            getPresenter().getAnswerSuccess(s);
        } else {
            getPresenter().getAnswerFailure();
        }

    }

    @Override
    public void DeleteImage(String deleteImagePath) {

        boolean b = FileIOUtils.deleteFilePic(deleteImagePath);
        if (b) {
            getPresenter().DeleteImageSuccess(BaseApplication.mContext.getResources().getString(R.string.phrases_24));
        } else {
            getPresenter().DeleteImageFailure(BaseApplication.mContext.getResources().getString(R.string.phrases_25));
        }
    }

    @Override
    public void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id, int type) {
        boolean b = SaveOrGetAnswers.saveToFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number + "_" + ht_id + "/", "AnswerAndRemark.txt", "1答案:" + answers + "&2备注:" + remark, false);
        if (b) {
            getPresenter().saveAnswersSuccess(type);
        } else {
            getPresenter().saveAnswersFailure(type);
        }
    }

    @Override
    public void SaveImages(final List<MediaBean> mediaBeanList, final String ht_ProjectId, final int number, final String ht_id) {

        int count = 0;
        for (int i = 0; i < mediaBeanList.size(); i++) {
            String originalPath = mediaBeanList.get(i).getOriginalPath();
            boolean b = FileIOUtils.copyFile(originalPath, Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number + "_" + ht_id + "/", FileIOUtils.getE(originalPath, "/"));
            count++;
        }
        if (count == mediaBeanList.size()) {
            getPresenter().SaveImagesSuccess();
        } else {
            getPresenter().SaveImagesFailure();
        }
    }

    @Override
    public void getAudio(final String ht_ProjectId, Activity activity, final int number, final String ht_id) {


        List<String> audiosList = FileIOUtils.getAudios(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number + "_" + ht_id);
        if (audiosList != null && audiosList.size() > 0) {
            getPresenter().getAudioSuccess(audiosList);
        } else {
            getPresenter().getAudioFailure(BaseApplication.mContext.getResources().getString(R.string.toast_15));
        }
    }

    @Override
    public void UpLoadFileList(String projectsDB_id, SubjectsDB subjectsDB) {
        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB_id + "/" + subjectsDB.getNumber() + "_" + subjectsDB.getHt_id());
        if (fileList != null && fileList.size() > 0) {
            getPresenter().getUpLoadFileListSuccess(fileList, projectsDB_id, subjectsDB);
        } else {
            getPresenter().getUpLoadFileListFailure(BaseApplication.mContext.getResources().getString(R.string.phrases_9));
        }
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
    public void startUpload(OSSClient ossClient, List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        upList.clear();
        String audioPath = null;
        int CountSize = 0;
        if (list != null && list.size() > 0) {
            if (fixedThreadPool == null) {
                fixedThreadPool = Executors.newFixedThreadPool(3);
            }
            uploadSuccess = 0;
            uploadFailure = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toLowerCase().endsWith("png") || list.get(i).toLowerCase().endsWith("jpeg")
                        || list.get(i).toLowerCase().endsWith("jpg") || list.get(i).toLowerCase().endsWith("gif")) {
                    CountSize++;
                    upList.add(list.get(i));
                } else if (list.get(i).toLowerCase().endsWith("wav") || list.get(i).toLowerCase().endsWith(".amr")
                        || list.get(i).toLowerCase().endsWith(".aac")) {
                    CountSize++;
                    upList.add(list.get(i));
                }

            }

            if (upList != null && upList.size() > 0) {
                for (int i = 0; i < upList.size(); i++) {
                    OSSAsyncTask ossAsyncTask = asyncPutImage(ossClient,
                            upList.get(i), CountSize, projectsDB, subjectsDB, list,  i + 1);
                }
            }

            if (upList != null && upList.size()==0) {
                if (getPresenter() != null) {
                    getPresenter().Pic_CompulsoryC(list, projectsDB, subjectsDB);
                }
            }

        }

    }

    @Override
    public void CallBack(Map<String, Object> map, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<UpLoadCallBack>(getApiService().upLoadCallBack(map)))
                .subscribeWith(new SimpleDisposableSubscriber<UpLoadCallBack>() {
                    @Override
                    public void _onNext(UpLoadCallBack upLoadCallBack) {
                        if (getPresenter() != null) {
                            if (upLoadCallBack.getResult() == 1) {
                                getPresenter().CallBackSuccess(projectsDB, subjectsDB,upLoadCallBack);
                            } else {
                                getPresenter().CallBackFailure(projectsDB, subjectsDB,upLoadCallBack);
                            }
                        }
                    }
                }));
    }

    @Override
    public void UpDataDb(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        SubjectsDB subjectsDB1 = new SubjectsDB();
        subjectsDB1.setsUploadStatus(1);
        subjectsDB1.setStage(1);
        update = subjectsDB1.update(subjectsDB.getId());
        List<SubjectsDB> subjectsDBList = projectsDB.getSubjectsDBList();
        if (subjectsDBList != null && subjectsDBList.size() > 0) {
            int up_subjectSize = 0;
            for (int i = 0; i < subjectsDBList.size(); i++) {
                if (subjectsDBList.get(i).getsUploadStatus() == 1) {
                    up_subjectSize++;
                }
            }
            if (up_subjectSize == subjectsDBList.size()) {
                ProjectsDB projectsDB1 = new ProjectsDB();
                projectsDB1.setIsComplete(1);
                projectsDB1.setpUploadStatus(1);
                update1 = projectsDB1.update(projectsDB.getId());
            }
        }

        if (update == 1 || update1 == 1) {
            if (getPresenter() != null) {
                getPresenter().UpDataDbSuccess();
            }
        } else {
            if (getPresenter() != null) {
                getPresenter().UpDataDbFailure(BaseApplication.mContext.getResources().getString(R.string.toast_32));
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
    public OSSAsyncTask asyncPutImage(OSS oss, String localFile, final int count, final ProjectsDB projectsDB, final SubjectsDB subjectsDB, final List<String> list, int picPosition) {
        if (localFile.equals("")) {
            return null;
        }
        File file = new File(localFile);
        if (!file.exists()) {
            return null;
        }
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String name = getName(localFile, "/");
                String object = null;
                if (getName(localFile, ".").toLowerCase().endsWith("wav")) {
                    String audioName = subjectsDB.getNumber()+".wav";
                    object = projectsDB.getPid() + "/audios/" + audioName;
                } else {
                    String picName = getName(localFile, ".").toLowerCase().equals("png") ? subjectsDB.getNumber() + "_" + picPosition + ".png"
                            : getName(localFile, ".").toLowerCase().equals("jpeg") ? subjectsDB.getNumber() + "_" + picPosition + ".jpeg"
                            : getName(localFile, ".").toLowerCase().equals("jpg") ? subjectsDB.getNumber() + "_" + picPosition + ".jpg"
                            : getName(localFile, ".").toLowerCase().equals("gif") ? subjectsDB.getNumber() + "_" + picPosition + ".gif"
                            : subjectsDB.getNumber() + "_" + picPosition + ".png";
                    object = projectsDB.getPid() + "/pictures/" + subjectsDB.getNumber() + "/" + picName;
                }
                PutObjectRequest put = new PutObjectRequest(Constants.BUCKET, object, localFile);

            /*    put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        if (getPresenter() != null) {
                            getPresenter().showProgress((int) currentSize, (int) totalSize, projectsDB.getPname() + "\n第" + subjectsDB.getNumber() + "题\n" + name);
                        }
                    }
                });*/
                put.setCRC64(OSSRequest.CRC64Config.YES);
                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        uploadSuccess++;
                        //每题的回调
                        if (getPresenter() != null) {
                            getPresenter().startUploadCallBack(list, uploadSuccess, uploadFailure, count, projectsDB, subjectsDB);
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
                        KLog.d("上传异常", info);
                        uploadFailure++;
                        if (getPresenter() != null) {
                            getPresenter().startUploadCallBack(list, uploadSuccess, uploadFailure, count, projectsDB, subjectsDB);
                        }
                    }
                });

            }
        });

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
