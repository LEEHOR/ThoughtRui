package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:07
 */
public interface UploadC  {
    interface View extends BaseContract.View {

        void getProjectListSuccess(List<ProjectsDB> projectsDB);
        void  getProjectListFailure(String failure);

        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position);
        void getSubjectListFailure(String failure);

        void getUpLoadFileListSuccess(List<String> list,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position,int subject_position);
        void getUpLoadFileListFailure(String failure);

        void StartUiProgressSuccess(PutObjectRequest request, int currentSize, int totalSize,String info);

        void UploadCallBack(List<String> list,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                            int project_position,int subject_position,
                            int uploadSuccessSize,int uploadFailSize,int totalSize);

        void CallBackServerSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                                   int project_position,int subject_position);
        void CallBackServerFailure(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                                   int project_position,int subject_position);

        void UpDataDbSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);
        void UpDataDbFailure(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);


    }

    interface Presenter extends BaseContract.Presenter {

        //获取项目列表
        void getProjectList(String sessionId);
        void getProjectListSuccess(List<ProjectsDB> projectsDB);
        void  getProjectListFailure(String failure);

        //获取题目列表
        void getSubjectList(List<ProjectsDB> projectsDBS,int position);
        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position);
        void getSubjectListFailure(String failure);

        //获取上传文件列表
        void UpLoadFileList(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position,int subject_position);
        void getUpLoadFileListSuccess(List<String> list,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position,int subject_position);
        void getUpLoadFileListFailure(String failure);

        //上传的Ui更新
        void StartUiProgressSuccess(PutObjectRequest request, int currentSize, int totalSize,String info);

        //开始上传
        void startUpLoad(OSSClient ossClient,List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);
        void UploadCallBack(List<String> list,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                            int project_position,int subject_position,
                            int uploadSuccessSize,int uploadFailSize,int totalSize);

        //回调上传
        void CallBackServer(Map<String,Object> map,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                      int project_position,int subject_position);
        void CallBackServerSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);
        void CallBackServerFailure(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);

        //更新数据库
        void UpDataDb(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                      int project_position,int subject_position,boolean success);
        void UpDataDbSuccess(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);
        void UpDataDbFailure(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                             int project_position,int subject_position);

    }

    interface Model extends BaseContract.Model {
        void getProjectList(String sessionId);
        void getSubjectList(List<ProjectsDB> projectsDBS,int position);
        void UpLoadFileList(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position,int subject_position);
        void startUpLoad(OSSClient ossClient,List<String> list,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,int project_position,int subject_position);
        void CallBackServer(Map<String,Object> map,List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                            int project_position,int subject_position);

        void UpDataDb(List<SubjectsDB> subjectsDBList,List<ProjectsDB> projectsDBS,
                      int project_position,int subject_position,boolean success);
    }
}
