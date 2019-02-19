package com.coahr.thoughtrui.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.UploadC;
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
public class UploadP extends BasePresenter<UploadC.View,UploadC.Model> implements UploadC.Presenter {
    @Inject
    public UploadP(UploadFragment mview, UploadM mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSTS(String utl) {
        if (mModle != null) {
            mModle.getSTS(utl);
        }
    }

    @Override
    public void getSTSSuccess(OSSCredentialProvider ossCredentialProvider) {
        if (getView() != null) {
            getView().getSTSSuccess(ossCredentialProvider);
        }
    }

    @Override
    public void getSTSFailure(String failure) {
        if (getView() != null) {
            getView().getSTSFailure(failure);
        }
    }

    @Override
    public void getOSS(Context context, String endpoint, OSSCredentialProvider credentialProvider, ClientConfiguration conf) {
        if (mModle != null) {
            mModle.getOSS(context, endpoint, credentialProvider, conf);
        }
    }

    @Override
    public void getOSSSuccess(OSS oss) {
        if (getView() != null) {
            getView().getOSSSuccess(oss);
        }
    }

    @Override
    public void getOSSFailure(String failure) {
        if (getView() != null) {
            getView().getOSSFailure(failure);
        }
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
    public void getSubjectList(ProjectsDB projectsDBList) {
        if (mModle != null) {
            mModle.getSubjectList(projectsDBList);
        }
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, ProjectsDB projectsdb) {
        if (getView() != null) {
            getView().getSubjectListSuccess(subjectsDBList,projectsdb);
        }
    }

    @Override
    public void getSubjectListFailure(String failure) {
        if (getView() != null) {
            getView().getSubjectListFailure(failure);
        }
    }

    @Override
    public void UpLoadFileList(ProjectsDB projectsDB, SubjectsDB subjectsDB, Activity activity) {
        if (mModle != null) {
            mModle.UpLoadFileList(projectsDB, subjectsDB, activity);
        }
    }

    @Override
    public void getUoLoadFileListSuccess(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().getUoLoadFileListSuccess(list,projectsDB,subjectsDB);
        }
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        if (getView() != null) {
            getView().getUpLoadFileListFailure(failure);
        }
    }

    @Override
    public void StartUpLoad(OSS oss,List<String> list,ProjectsDB projectsDB, SubjectsDB  subjectsDB) {
        if (mModle != null) {
            mModle.StartUpLoad(oss,list,projectsDB,subjectsDB);
        }
    }

    @Override
    public void StartUpLoadSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB,List<String> list) {
        if (getView() != null) {
            getView().StartUpLoadSuccess(projectsDB,subjectsDB,list);
        }
    }

    @Override
    public void StartUpLoadFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().StartUpLoadFailure(projectsDB,subjectsDB);
        }
    }

    @Override
    public void StartUiProgressSuccess(PutObjectRequest request, int currentSize, int totalSize,String info) {
        if (getView() != null) {
            getView().StartUiProgressSuccess(request,currentSize,totalSize,info);
        }
    }

    @Override
    public void CallBack(Map<String, Object> map, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.CallBack(map,projectsDB,subjectsDB);
        }
    }

    @Override
    public void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackSuccess(projectsDB,subjectsDB);
        }
    }

    @Override
    public void CallBackFailure(ProjectsDB projectsDB,SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackFailure(projectsDB, subjectsDB);
        }
    }
}
