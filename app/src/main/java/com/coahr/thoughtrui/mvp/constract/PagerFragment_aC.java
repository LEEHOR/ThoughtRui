package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface PagerFragment_aC {

    interface View extends BaseContract.View {


        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void getAnswerSuccess(AnswersDB answersDB);

        void getAnswerFailure();

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void saveAnswersSuccess();

        void saveAnswersFailure();

        void SaveImagesSuccess();

        void SaveImagesFailure();

    }

     interface Presenter extends BaseContract.Presenter {

        void getSubject(String DbProjectId,int position);

        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImage(String ht_ProjectId,int position);

        void getImageSuccess(List<String> imagePathList);

        void getImageFailure();

        void DeleteImage(String deleteImagePath);

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void getAnswer(int subject_id);

         void getAnswerSuccess(AnswersDB answersDB);

        void getAnswerFailure();

         void saveAnswers(String answers, String remark);

         void saveAnswersSuccess();

         void saveAnswersFailure();

         void SaveImages(List<MediaBean> mediaBeanList,String ht_ProjectId,int position);

         void SaveImagesSuccess();

         void SaveImagesFailure();

    }

     interface Model extends BaseContract.Model {


         void getSubject(String DbProjectId,int position);

        void getImage(String ht_ProjectId,int position);

        void getAnswer(int subject_id);

         void DeleteImage(String deleteImagePath);

         void saveAnswers(String answers, String remark);

         void SaveImages(List<MediaBean> mediaBeanList,String ht_ProjectId,int position);

    }
}
