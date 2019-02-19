package com.coahr.thoughtrui.mvp.presenter;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.PagerFragment_aM;
import com.coahr.thoughtrui.mvp.view.startProject.PagerFragment_a;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

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
    public void getSubject(String DbProjectId, String ht_ProjectId, Activity activity,int number,String ht_id) {
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
    public void getImage(String ht_ProjectId,Activity activity,int number,String ht_id) {
        if (mModle != null) {
            mModle.getImage(ht_ProjectId,activity, number, ht_id);
        }
    }

    @Override
    public void getImageSuccess(List<String> imagesDBList) {
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
    public void getAnswer(String ht_ProjectId,Activity activity,int number,String ht_id) {
        if (mModle != null) {
            mModle.getAnswer(ht_ProjectId,activity,number,ht_id);
        }
    }

    @Override
    public void getAnswerSuccess(String message) {
        if (getView() != null) {
            getView().getAnswerSuccess(message);
        }
    }

    @Override
    public void getAnswerFailure() {
        if (getView() != null) {
            getView().getAnswerFailure();
        }
    }

    @Override
    public void saveAnswers(String answers,String remark,String ht_ProjectId,int number,String ht_id) {
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
    public void SaveImages(List<MediaBean> mediaBeanList,String ht_ProjectId,int number,String ht_id) {
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
    public void getAudio(String ht_ProjectId,Activity activity,int number,String ht_id) {
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

    @Override
    public void UpLoadFileList(String projectsDB_id, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.UpLoadFileList(projectsDB_id,subjectsDB);
        }
    }

    @Override
    public void getUoLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().getUoLoadFileListSuccess(list,projectsDB_id,subjectsDB);
        }
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        if (getView() != null) {
            getView().getUpLoadFileListFailure(failure);
        }
    }

    @Override
    public void CallBack(Map<String, Object> map, String projectsDB_id, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.CallBack(map,projectsDB_id,subjectsDB);
        }
    }

    @Override
    public void CallBackSuccess(String projectsDB_id, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackSuccess(projectsDB_id,subjectsDB);
        }
    }

    @Override
    public void CallBackFailure(String projectsDB_id, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackFailure(projectsDB_id, subjectsDB);
        }
    }
}
