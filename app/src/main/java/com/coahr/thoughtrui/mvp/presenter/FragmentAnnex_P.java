package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.FragmentAnnex_C;
import com.coahr.thoughtrui.mvp.constract.FragmentAnnex_C.Presenter;
import com.coahr.thoughtrui.mvp.model.Bean.AnnexDate;
import com.coahr.thoughtrui.mvp.model.FragmentAnnex_M;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnex;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：
 */
public class FragmentAnnex_P extends BasePresenter<FragmentAnnex_C.View,FragmentAnnex_C.Model> implements FragmentAnnex_C.Presenter {
        @Inject
    public FragmentAnnex_P(FragmentAnnex mview, FragmentAnnex_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSubject(ProjectsDB projectsDB) {
        if (mModle != null) {
            mModle.getSubject(projectsDB);
        }
    }

    @Override
    public void getSubjectSuccess(List<SubjectsDB> subjectsDB,ProjectsDB projectsDB) {
        if (getView() != null) {
            getView().getSubjectSuccess(subjectsDB,projectsDB);
        }
    }

    @Override
    public void getSubjectFailure(String failure) {
        if (getView() != null) {
            getView().getSubjectFailure(failure);
        }
    }

    @Override
    public void getFileList(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB) {
        if (mModle != null) {
            mModle.getFileList(subjectsDBList,projectsDB);
        }
    }

    @Override
    public void getFileListSuccess(AnnexDate annexDate) {
        if (getView() != null) {
            getView().getFileListSuccess(annexDate);
        }
    }

    @Override
    public void getFileListFailure(String failure) {
        if (getView() != null) {
            getView().getFileListFailure(failure);
        }
    }
}
