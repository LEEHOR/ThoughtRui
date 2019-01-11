package com.coahr.thoughtrui.mvp.presenter;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReViewStart_C;
import com.coahr.thoughtrui.mvp.model.ReViewStart_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStart;

import java.util.List;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
public class ReViewStart_P extends BasePresenter<ReViewStart_C.View,ReViewStart_C.Model>implements ReViewStart_C.Presenter {
    @Inject
    public ReViewStart_P(ReViewStart mview, ReViewStart_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSubject(String DbProjectId, String ht_ProjectId, Activity activity, int number, String ht_id) {
        if (mModle != null) {
            mModle.getSubject(DbProjectId,ht_ProjectId,activity,number,ht_id);
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
    public void getImage(String ht_ProjectId, Activity activity, int number, String ht_id) {
        if (mModle != null) {
            mModle.getImage(ht_ProjectId,activity,number,ht_id);
        }
    }

    @Override
    public void getImageSuccess(List<String> imagePathList) {
        if (getView() != null) {
            getView().getImageSuccess(imagePathList);
        }
    }

    @Override
    public void getImageFailure() {
        if (getView() != null) {
            getView().getImageFailure();
        }
    }

    @Override
    public void DeleteImage(String deleteImagePath) {
        if (mModle != null) {
            mModle.DeleteImage(deleteImagePath);
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
    public void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id) {
        if (mModle != null) {
            mModle.getAnswer(ht_ProjectId,activity,number,ht_id);
        }
    }

    @Override
    public void getAnswerSuccess(String Massage) {
        if (getView() != null) {
            getView().getAnswerSuccess(Massage);
        }
    }

    @Override
    public void getAnswerFailure() {
        if (getView() != null) {
            getView().getAnswerFailure();
        }
    }

    @Override
    public void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id) {
        if (mModle != null) {
            mModle.saveAnswers(answers,remark,ht_ProjectId,number,ht_id);
        }
    }

    @Override
    public void saveAnswersSuccess() {
        if (getView() != null) {
            getView().saveAnswersSuccess();
        }
    }

    @Override
    public void saveAnswersFailure() {
        if (getView() != null) {
            getView().saveAnswersFailure();
        }
    }

    @Override
    public void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id) {
        if (mModle != null) {
            mModle.SaveImages(mediaBeanList,ht_ProjectId,number,ht_id);
        }
    }

    @Override
    public void SaveImagesSuccess() {
        if (getView() != null) {
            getView().SaveImagesSuccess();
        }
    }

    @Override
    public void SaveImagesFailure() {
        if (getView() != null) {
            getView().SaveImagesFailure();
        }
    }

    @Override
    public void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id) {
        if (mModle != null) {
            mModle.getAudio(ht_ProjectId,activity,number,ht_id);
        }
    }

    @Override
    public void getAudioSuccess(List<String> audioList) {
        if (getView() != null) {
            getView().getAudioSuccess(audioList);
        }
    }

    @Override
    public void getAudioFailure(String failure) {
        if (getView() != null) {
            getView().getAudioFailure(failure);
        }
    }
}
