package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
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

        void getSTSSuccess(OSSCredentialProvider ossCredentialProvider);
        void getSTSFailure(String failure);

        void  getOSSSuccess( OSS oss);
        void  getOSSFailure(String failure);

        void getProjectListSuccess(List<ProjectsDB> projectsDB);
        void  getProjectListFailure(String failure);

        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB);
        void getSubjectListFailure(String failure);

        void getUoLoadFileListSuccess(List<String> list,ProjectsDB projectsDB, SubjectsDB  subjectsDB);
        void getUpLoadFileListFailure(String failure);

        void StartUpLoadSuccess(ProjectsDB projectsDB, SubjectsDB  subjectsDB,  List<String> list);
        void StartUpLoadFailure(ProjectsDB projectsDB, SubjectsDB  subjectsDB);

        void StartUiProgressSuccess(PutObjectRequest request, int currentSize, int totalSize,String info);

        void CallBackSuccess(ProjectsDB projectsDB,SubjectsDB subjectsDB);
        void CallBackFailure(ProjectsDB projectsDB,SubjectsDB subjectsDB);

    }

    interface Presenter extends BaseContract.Presenter {
        //AK鉴权
        void getSTS(String utl);
        void getSTSSuccess(OSSCredentialProvider ossCredentialProvider);
        void getSTSFailure(String failure);
        //OSS
        void getOSS(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf);
        void  getOSSSuccess( OSS oss);
        void  getOSSFailure(String failure);
        //获取项目列表
        void getProjectList(String sessionId);
        void getProjectListSuccess(List<ProjectsDB> projectsDB);
        void  getProjectListFailure(String failure);

        //获取题目列表
        void getSubjectList(ProjectsDB projectsDB);
        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsdb);
        void getSubjectListFailure(String failure);

        //获取上传文件列表
        void UpLoadFileList(ProjectsDB projectsDB, SubjectsDB  subjectsDB, Activity activity);
        void getUoLoadFileListSuccess(List<String> list,ProjectsDB projectsDB, SubjectsDB  subjectsDB);
        void getUpLoadFileListFailure(String failure);
        //开始上传
        void StartUpLoad(OSS oss,List<String> list,ProjectsDB projectsDB, SubjectsDB  subjectsDB);
        void StartUpLoadSuccess(ProjectsDB projectsDB, SubjectsDB  subjectsDB,  List<String> list);
        void StartUpLoadFailure(ProjectsDB projectsDB, SubjectsDB  subjectsDB);

        //上传的Ui更新
        void StartUiProgressSuccess(PutObjectRequest request, int currentSize, int totalSize,String info);

        //每题上传完成后的回调
        void CallBack(Map<String,Object> map,ProjectsDB projectsDB,SubjectsDB subjectsDB);
        void CallBackSuccess(ProjectsDB projectsDB,SubjectsDB subjectsDB);
        void CallBackFailure(ProjectsDB projectsDB,SubjectsDB subjectsDB);
    }

    interface Model extends BaseContract.Model {
        void getSTS(String utl);
        void getOSS(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf);
        void getProjectList(String sessionId);
        void getSubjectList(ProjectsDB projectsDB);
        void UpLoadFileList(ProjectsDB projectsDB, SubjectsDB  subjectsDB, Activity activity);
        void StartUpLoad(OSS oss,List<String> list,ProjectsDB projectsDB, SubjectsDB  subjectsDB);
        void CallBack(Map<String,Object> map,ProjectsDB projectsDB,SubjectsDB subjectsDB);

    }
}
