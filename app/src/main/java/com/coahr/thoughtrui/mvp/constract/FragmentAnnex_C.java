package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface FragmentAnnex_C {

    interface View extends BaseContract.View {


        void getSubjectSuccess(List<SubjectsDB> subjectsDB,ProjectsDB projectsDB);

        void getSubjectFailure(String failure);

        void getFileListSuccess(List<List<String>> fileList);

        void getFileListFailure(String failure);
    }

     interface Presenter extends BaseContract.Presenter {

        void getSubject(ProjectsDB projectsDB);

        void getSubjectSuccess(List<SubjectsDB> subjectsDB,ProjectsDB projectsDB);

        void getSubjectFailure(String failure);

        void getFileList(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB);

        void getFileListSuccess(List<List<String>> fileList);

        void getFileListFailure(String failure);
    }

     interface Model extends BaseContract.Model {


         void getSubject(ProjectsDB projectsDB);
         void getFileList(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB);

    }
}
