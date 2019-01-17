package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ProjectDetailFragment_C;
import com.coahr.thoughtrui.mvp.constract.ProjectSuccessDialog_C;
import com.coahr.thoughtrui.mvp.model.ProjectDetailFragment_M;
import com.coahr.thoughtrui.mvp.model.ProjectSuccessDialog_M;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：
 */
public class ProjectDetailFragment_P extends BasePresenter<ProjectDetailFragment_C.View,ProjectDetailFragment_C.Model>implements ProjectDetailFragment_C.Presenter {
    @Inject
    public ProjectDetailFragment_P(ProjectDetailFragment mview, ProjectDetailFragment_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSubjectList(ProjectsDB projectsDB) {
        if (mModle != null) {
            mModle.getSubjectList(projectsDB);
        }
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB,int totalSize) {
        if (getView() != null) {
            getView().getSubjectListSuccess(subjectsDBList,projectsDB,totalSize);
        }
    }

    @Override
    public void getSubjectListFailure(String failure) {
        if (getView() != null) {
            getView().getSubjectListFailure(failure);
        }
    }

    @Override
    public void getDateSize(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB) {
        if (mModle != null) {
            mModle.getDateSize(subjectsDBList,projectsDB);
        }
    }

    @Override
    public void getDateSizeSuccess(int subject, int files) {
        if (getView() != null) {
            getView().getDateSizeSuccess(subject,files);
        }
    }

    @Override
    public void getDateSizeFailure(String failure) {
        if (getView() != null) {
            getView().getDateSizeFailure(failure);
        }
    }
}
