package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.PagerFragment_aM;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 23:12
 */
public class PagerFragment_aP extends BasePresenter<PagerFragment_aC.View,PagerFragment_aC.Model> implements PagerFragment_aC.Presenter {
    @Inject
    public PagerFragment_aP(PagerFragment_a mview, PagerFragment_aM mModel) {
        super(mview, mModel);
    }



    @Override
    public void getSubject(String DbProjectId, int position) {
        if (mModle != null) {
            mModle.getSubject(DbProjectId,position);
        }
    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().getSubjectSuccess(subjectsDB);
        }
    }

    @Override
    public void getSubjectFailure(String failure) {
        if (getView() != null) {
            getView().getSubjectFailure(failure);
        }
    }

    @Override
    public void getImage(int subject_id) {
        if (mModle != null) {
            mModle.getImage(subject_id);
        }
    }

    @Override
    public void getImageSuccess(List<ImagesDB> imagesDBList) {
        if (getView() != null) {
            getView().getImageSuccess(imagesDBList);
        }
    }

    @Override
    public void getImageFailure() {
        if (getView() != null) {
            getView().getImageFailure();
        }
    }

    @Override
    public void DeleteImage(int imageId,String imageName) {
        if (mModle != null) {
            mModle.DeleteImage(imageId,imageName);
        }
    }

    @Override
    public void DeleteImageSuccess(String Massage) {
        if (getView() != null) {
            getView().DeleteImageSuccess(Massage);
        }
    }

    @Override
    public void DeleteImageFailure(String Massage) {
        if (getView() != null) {
            getView().DeleteImageFailure(Massage);
        }
    }

    @Override
    public void getAnswer(int subject_id) {
        if (mModle != null) {
            mModle.getAnswer(subject_id);
        }
    }

    @Override
    public void getAnswerSuccess(List<AnswersDB> answersDBList) {
        if (getView() != null) {
            getView().getAnswerSuccess(answersDBList);
        }
    }

    @Override
    public void getAnswerFailure() {
        if (getView() != null) {
            getView().getAnswerFailure();
        }
    }
}
