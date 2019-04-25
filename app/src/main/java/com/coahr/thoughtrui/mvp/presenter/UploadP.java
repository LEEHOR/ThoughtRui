package com.coahr.thoughtrui.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.coahr.thoughtrui.mvp.model.UploadM;
import com.coahr.thoughtrui.mvp.view.upload.UploadFragment;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:10
 */
public class UploadP extends BasePresenter<UploadC.View, UploadC.Model> implements UploadC.Presenter {
    @Inject
    public UploadP(UploadFragment mview, UploadM mModel) {
        super(mview, mModel);
    }

    @Override
    public void getProjectList(String sessionId) {
        if (mModle != null) {
            mModle.getProjectList(sessionId);
        }
    }

    @Override
    public void getProjectListSuccess(List<ProjectsDB> projectsDB) {
        if (getView() != null) {
            getView().getProjectListSuccess(projectsDB);
        }
    }

    @Override
    public void getProjectListFailure(String failure) {
        if (getView() != null) {
            getView().getProjectListFailure(failure);
        }
    }

    @Override
    public void getSubjectList(List<ProjectsDB> projectsDBS, int position) {
        if (mModle != null) {
            mModle.getSubjectList(projectsDBS, position);
        }
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position) {
        if (getView() != null) {
            getView().getSubjectListSuccess(subjectsDBList, projectsDBS, project_position);
        }
    }

    @Override
    public void getSubjectListFailure(String failure, List<ProjectsDB> projectsDBS, int project_position) {
        if (getView() != null) {
            getView().getSubjectListFailure(failure, projectsDBS, project_position);
        }
    }

    @Override
    public void UpLoadFileList(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (mModle != null) {
            mModle.UpLoadFileList(subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }

    @Override
    public void getUpLoadFileListSuccess(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (getView() != null) {
            getView().getUpLoadFileListSuccess(list, subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }


    @Override
    public void getUpLoadFileListFailure(String failure) {
        if (getView() != null) {
            getView().getUpLoadFileListFailure(failure);
        }
    }

    @Override
    public void StartUiProgressSuccess(int currentSize, int totalSize, String info) {
        if (getView() != null) {
            getView().StartUiProgressSuccess(currentSize, totalSize, info);
        }
    }

    @Override
    public void getOss(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getOss(map);
        }
    }

    @Override
    public void getOssSuccess(AliyunOss aliyunOss) {
        if (getView() != null) {
            getView().getOssSuccess(aliyunOss);
        }
    }

    @Override
    public void getOssFailure(int  statusCode) {
        if (getView() != null) {
            getView().getOssFailure(statusCode);
        }
    }

    @Override
    public void startUpLoad(OSSClient ossClient, List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (mModle != null) {
            mModle.startUpLoad(ossClient, list, subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }

    @Override
    public void UploadCallBack(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, List<String> picList, int project_position, int subject_position, int uploadSuccessSize, int uploadFailSize, int totalSize) {
        if (getView() != null) {
            getView().UploadCallBack(list, subjectsDBList, projectsDBS, picList, project_position, subject_position, uploadSuccessSize, uploadFailSize, totalSize);
        }
    }

    @Override
    public void Pic_Compulsory(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (getView() != null) {
            getView().Pic_Compulsory(list, subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }


    @Override
    public void CallBackServer(Map<String, Object> map, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (mModle != null) {
            mModle.CallBackServer(map, subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }

    @Override
    public void CallBackServerSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, UpLoadCallBack upLoadCallBack) {
        if (getView() != null) {
            getView().CallBackServerSuccess(subjectsDBList, projectsDBS, project_position, subject_position, upLoadCallBack);
        }
    }

    @Override
    public void CallBackServerFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, UpLoadCallBack upLoadCallBack) {
        if (getView() != null) {
            getView().CallBackServerFailure(subjectsDBList, projectsDBS, project_position, subject_position, upLoadCallBack);
        }
    }

    @Override
    public void UpDataDb(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, boolean success) {
        if (mModle != null) {
            mModle.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, success);
        }
    }

    @Override
    public void UpDataDbSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (getView() != null) {
            getView().UpDataDbSuccess(subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }

    @Override
    public void UpDataDbFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        if (getView() != null) {
            getView().UpDataDbFailure(subjectsDBList, projectsDBS, project_position, subject_position);
        }
    }

}
