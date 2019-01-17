package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
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
public interface ProjectSuccessDialog_C {
    interface View extends BaseContract.View {
        void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB,int totalSize);
        void getSubjectListFailure(String failure);

        void getDateSizeSuccess(int subject,int files );
        void getDateSizeFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {
            void getSubjectList(ProjectsDB projectsDB);
            void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB,int totalSize);
            void getSubjectListFailure(String failure);

            void getDateSize(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB);
            void getDateSizeSuccess(int subject,int files );
            void getDateSizeFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void getSubjectList(ProjectsDB projectsDB);
        void getDateSize(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB);
    }
}
