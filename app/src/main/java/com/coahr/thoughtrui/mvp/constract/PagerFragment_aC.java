package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface PagerFragment_aC {

    interface View extends BaseContract.View {


        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImageSuccess(List<ImagesDB> imagesDBList);

        void getImageFailure();

        void getAnswerSuccess(List<AnswersDB> answersDBList);

        void getAnswerFailure();

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

    }

     interface Presenter extends BaseContract.Presenter {

        void getSubject(String DbProjectId,int position);

        void getSubjectSuccess(SubjectsDB subjectsDB);

        void getSubjectFailure(String failure);

        void getImage(int subject_id);

        void getImageSuccess(List<ImagesDB> imagesDBList);

        void getImageFailure();

        void DeleteImage(int imageId,String imageName);

        void DeleteImageSuccess(String Massage);

        void DeleteImageFailure(String Massage);

        void getAnswer(int subject_id);

        void getAnswerSuccess(List<AnswersDB> answersDBList);

        void getAnswerFailure();


    }

     interface Model extends BaseContract.Model {


         void getSubject(String DbProjectId,int position);

        void getImage(int subject_id);

        void getAnswer(int subject_id);

         void DeleteImage(int imageId,String imagePath);

    }
}
