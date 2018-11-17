package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface StartProjectFragmentC {
    interface View extends BaseContract.View {


        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure);

        void getOfflineSuccess(SubjectsDB subjectsDB);

        void getOfflineFailure(int failure);

    }

    interface Presenter extends BaseContract.Presenter {


        void getMainData(Map map);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure);

        void getOfflineDate();

        void getOfflineSuccess(SubjectsDB subjectsDB);

        void getOfflineFailure(int failure);


    }

    interface Model extends BaseContract.Model {


        void getMainData(Map map);


        void getOfflineDate();

    }
}
