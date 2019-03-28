package com.coahr.thoughtrui.mvp.presenter;

import android.app.Activity;

import com.alibaba.sdk.android.oss.OSSClient;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ReViewStartAnswering_C;
import com.coahr.thoughtrui.mvp.model.ReViewStartAnswering_M;
import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStartAnswering;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
public class ReViewStartAnswering_P extends BasePresenter<ReViewStartAnswering_C.View, ReViewStartAnswering_C.Model> implements ReViewStartAnswering_C.Presenter {
    @Inject
    public ReViewStartAnswering_P(ReViewStartAnswering mview, ReViewStartAnswering_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getSubject(String DbProjectId, String ht_ProjectId, Activity activity, int number, String ht_id) {
        if (mModle != null) {
            mModle.getSubject(DbProjectId, ht_ProjectId, activity, number, ht_id);
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
            mModle.getImage(ht_ProjectId, activity, number, ht_id);
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
            mModle.getAnswer(ht_ProjectId, activity, number, ht_id);
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
    public void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id,int type) {
        if (mModle != null) {
            mModle.saveAnswers(answers, remark, ht_ProjectId, number, ht_id,type);
        }
    }

    @Override
    public void saveAnswersSuccess(int type) {
        if (getView() != null) {
            getView().saveAnswersSuccess(type);
        }
    }

    @Override
    public void saveAnswersFailure(int type) {
        if (getView() != null) {
            getView().saveAnswersFailure(type);
        }
    }

    @Override
    public void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id) {
        if (mModle != null) {
            mModle.SaveImages(mediaBeanList, ht_ProjectId, number, ht_id);
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
            mModle.getAudio(ht_ProjectId, activity, number, ht_id);
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
            mModle.UpLoadFileList(projectsDB_id, subjectsDB);
        }
    }

    @Override
    public void getUpLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().getUpLoadFileListSuccess(list, projectsDB_id, subjectsDB);
        }
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        if (getView() != null) {
            getView().getUpLoadFileListFailure(failure);
        }
    }

    @Override
    public void startUpload(OSSClient ossClient, List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.startUpload(ossClient, list, projectsDB, subjectsDB);
        }
    }

    @Override
    public void startUploadCallBack(List<String> list, int uploadSuccessSize, int uploadFailSize, int totalSize, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().startUploadCallBack(list, uploadSuccessSize, uploadFailSize, totalSize, projectsDB, subjectsDB);
        }
    }

    @Override
    public void Up_Pic_Compulsory(ProjectsDB projectsDB, SubjectsDB subjectsDB,List<String> list) {
        if (getView() != null) {
            getView().Up_Pic_Compulsory(projectsDB, subjectsDB,list);
        }
    }

    @Override
    public void showProgress(int currentSize, int totalSize, String info) {
        if (getView() != null) {
            getView().showProgress(currentSize, totalSize, info);
        }
    }

    @Override
    public void CallBack(Map<String, Object> map, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.CallBack(map, projectsDB, subjectsDB);
        }
    }

    @Override
    public void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackSuccess(projectsDB, subjectsDB);
        }
    }

    @Override
    public void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (getView() != null) {
            getView().CallBackFailure(projectsDB, subjectsDB);
        }
    }

    @Override
    public void UpDataDb(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        if (mModle != null) {
            mModle.UpDataDb(projectsDB, subjectsDB);
        }
    }

    @Override
    public void UpDataDbSuccess() {
        if (getView() != null) {
            getView().UpDataDbSuccess();
        }
    }

    @Override
    public void UpDataDbFailure(String fail) {
        if (getView() != null) {
            getView().UpDataDbFailure(fail);
        }
    }
}
