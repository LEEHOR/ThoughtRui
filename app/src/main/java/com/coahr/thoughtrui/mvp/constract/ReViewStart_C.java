package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface ReViewStart_C {

    interface View extends BaseContract.View {


        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void getAnswerSuccess(String Massage);

        void getAnswerFailure();

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void saveAnswersSuccess();

        void saveAnswersFailure();

        void SaveImagesSuccess();

        void SaveImagesFailure();

        void getAudioSuccess(List<String> audioList);

        void getAudioFailure(String failure);

    }

     interface Presenter extends BaseContract.Presenter {

        void getSubject(String DbProjectId, String ht_ProjectId, Activity activity, int number, String ht_id);

        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImage(String ht_ProjectId, Activity activity, int number, String ht_id);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void DeleteImage(String deleteImagePath);

         void DeleteImageSuccess(String Massage);

         void DeleteImageFailure(String Massage);

         void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id);

         void getAnswerSuccess(String Massage);

         void getAnswerFailure();

         void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id);

         void saveAnswersSuccess();

         void saveAnswersFailure();

         void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id);

         void SaveImagesSuccess();

         void SaveImagesFailure();

         void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id);

         void getAudioSuccess(List<String> audioList);

         void getAudioFailure(String failure);

    }

     interface Model extends BaseContract.Model {


         void getSubject(String DbProjectId, String ht_ProjectId, Activity activity, int number, String ht_id);

         void getImage(String ht_ProjectId, Activity activity, int number, String ht_id);

         void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id);

         void DeleteImage(String deleteImagePath);

         void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id);

         void SaveImages(List<MediaBean> mediaBeanList, String ht_ProjectId, int number, String ht_id);

         void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id);

    }
}
