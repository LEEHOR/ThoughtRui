package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;

import java.util.List;
import java.util.Map;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface PagerFragment_aC {

    interface View extends BaseContract.View {


        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void getAnswerSuccess(String Massage);

        void getAnswerFailure();

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void saveAnswersSuccess(int type);

        void saveAnswersFailure(int type);

        void SaveImagesSuccess();

        void SaveImagesFailure();

        void getAudioSuccess(List<String> audioList);

        void getAudioFailure(String failure);


        void getUpLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB);

        void getUpLoadFileListFailure(String failure);

        //Oss

        void getOssSuccess(AliyunOss aliyunOss);

        void getOssFailure(int statusCode);

        void startUploadCallBack(List<String> list, int uploadSuccessSize, int uploadFailSize, int totalSize, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void Pic_CompulsoryC(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void Pic_CompulsoryD(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void showProgress(int currentSize, int totalSize, String info);


        void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB, UpLoadCallBack upLoadCallBack);

        void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB, UpLoadCallBack upLoadCallBack);


        void UpDataDbSuccess();

        void UpDataDbFailure(String fail);

    }

    interface Presenter extends BaseContract.Presenter {

        void getSubject(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImage(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void DeleteImage(String deleteImagePath);

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getAnswerSuccess(String Massage);

        void getAnswerFailure();

        void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id, int type);

        void saveAnswersSuccess(int type);

        void saveAnswersFailure(int type);

        void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id);

        void SaveImagesSuccess();

        void SaveImagesFailure();

        void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getAudioSuccess(List<String> audioList);

        void getAudioFailure(String failure);

        //获取上传文件列表
        void UpLoadFileList(String projectsDB_id, SubjectsDB subjectsDB);

        void getUpLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB);

        void getUpLoadFileListFailure(String failure);

        //getOss
        void getOss(Map<String, Object> map);

        void getOssSuccess(AliyunOss aliyunOss);

        void getOssFailure(int statusCode);

        //开始上传
        void startUpload(OSSClient ossClient, List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void startUploadCallBack(List<String> list, int uploadSuccessSize, int uploadFailSize, int totalSize, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void Pic_CompulsoryC(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void Pic_CompulsoryD(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void showProgress(int currentSize, int totalSize, String info);

        //每题上传完成后的回调
        void CallBack(Map<String, Object> map, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB, UpLoadCallBack upLoadCallBack);

        void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB, UpLoadCallBack upLoadCallBack);

        //更新数据库
        void UpDataDb(ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void UpDataDbSuccess();

        void UpDataDbFailure(String fail);


    }

    interface Model extends BaseContract.Model {


        void getSubject(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getImage(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id);

        void DeleteImage(String deleteImagePath);

        void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id, int type);

        void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id);

        void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id);

        void UpLoadFileList(String projectsDB_id, SubjectsDB subjectsDB);

        void getOss(Map<String, Object> map);

        void startUpload(OSSClient ossClient, List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void CallBack(Map<String, Object> map, ProjectsDB projectsDB, SubjectsDB subjectsDB);

        void UpDataDb(ProjectsDB projectsDB, SubjectsDB subjectsDB);
    }
}
