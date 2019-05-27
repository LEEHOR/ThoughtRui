package com.coahr.thoughtrui.mvp.constract;

import com.alibaba.sdk.android.oss.OSSClient;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:07
 */
public interface UploadC {
    interface View extends BaseContract.View {

        void getProjectListSuccess(List<ProjectsDB> projectsDB);

        void getProjectListFailure(String failure);

        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position);

        void getSubjectListFailure(String failure, List<ProjectsDB> projectsDBS, int project_position);

        void getUpLoadFileListSuccess(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void getUpLoadFileListFailure(String failure);

        void getOssSuccess(AliyunOss aliyunOss);

        void getOssFailure(int  statusCode);

        void StartUiProgressSuccess(int currentSize, int totalSize, String info);

        void UploadCallBack(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, List<String> pic,
                            int project_position, int subject_position,
                            int uploadSuccessSize, int uploadFailSize, int totalSize);

        void Pic_Compulsory(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                            int project_position, int subject_position);

        void CallBackServerSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                                   int project_position, int subject_position, UpLoadCallBack upLoadCallBack);

        void CallBackServerFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                                   int project_position, int subject_position, UpLoadCallBack upLoadCallBack);


        void UpDataDbSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                             int project_position, int subject_position);

        void UpDataDbFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                             int project_position, int subject_position);


    }

    interface Presenter extends BaseContract.Presenter {

        //获取项目列表
        void getProjectList(String sessionId);

        void getProjectListSuccess(List<ProjectsDB> projectsDB);

        void getProjectListFailure(String failure);

        //获取题目列表
        void getSubjectList(List<ProjectsDB> projectsDBS, int position);

        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position);

        void getSubjectListFailure(String failure, List<ProjectsDB> projectsDBS, int project_position);

        //获取上传文件列表
        void UpLoadFileList(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void getUpLoadFileListSuccess(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void getUpLoadFileListFailure(String failure);

        //上传的Ui更新
        void StartUiProgressSuccess(int currentSize, int totalSize, String info);

        //Oss
        void getOss(Map<String, Object> map);

        void getOssSuccess(AliyunOss aliyunOss);

        void getOssFailure(int  statusCode);

        //开始上传
        void startUpLoad(OSSClient ossClient, List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void UploadCallBack(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, List<String> pic,
                            int project_position, int subject_position,
                            int uploadSuccessSize, int uploadFailSize, int totalSize);

        void Pic_Compulsory(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                            int project_position, int subject_position);

        //回调上传
        void CallBackServer(Map<String, Object> map, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                            int project_position, int subject_position);

        void CallBackServerSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                                   int project_position, int subject_position, UpLoadCallBack upLoadCallBack);

        void CallBackServerFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                                   int project_position, int subject_position, UpLoadCallBack upLoadCallBack);

        //更新数据库
        void UpDataDb(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                      int project_position, int subject_position, boolean success);

        void UpDataDbSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                             int project_position, int subject_position);

        void UpDataDbFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                             int project_position, int subject_position);

    }

    interface Model extends BaseContract.Model {
        void getProjectList(String sessionId);

        void getSubjectList(List<ProjectsDB> projectsDBS, int position);

        void UpLoadFileList(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void startUpLoad(OSSClient ossClient, List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position);

        void CallBackServer(Map<String, Object> map, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                            int project_position, int subject_position);

        void UpDataDb(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS,
                      int project_position, int subject_position, boolean success);

        void getOss(Map<String, Object> map);
    }
}
