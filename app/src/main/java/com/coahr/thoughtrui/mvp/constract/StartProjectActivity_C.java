package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface StartProjectActivity_C {
    interface View extends BaseContract.View {


        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure);

        void getOfflineSuccess(int size, String dbProjectId, String ht_projectId);

        void getOfflineFailure(int failure);

    }

    interface Presenter extends BaseContract.Presenter {


        void getMainData(Map map);

        void getMainDataSuccess(QuestionBean questionBean);

        void getMainDataFailure(String failure);

        void getOfflineDate(String dbProjectId, String ht_projectId);

        void getOfflineSuccess(int size, String dbProjectId, String ht_projectId);

        void getOfflineFailure(int failure);


    }

    interface Model extends BaseContract.Model {


        void getMainData(Map map);


        void getOfflineDate(String dbProjectId, String ht_projectId);

    }
}
