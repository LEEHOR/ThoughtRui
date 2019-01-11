package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface ChangePassWord_C {

    interface View extends BaseContract.View {

        void  getChangePassSuccess(ChangePassWord changePassWord);

        void  getChangePassFailure(String failure);
    }

     interface Presenter extends BaseContract.Presenter {
        void  getChangePass(Map<String,Object> map);

        void  getChangePassSuccess(ChangePassWord changePassWord);

        void  getChangePassFailure(String failure);

    }

     interface Model extends BaseContract.Model {

         void  getChangePass(Map<String,Object> map);
    }
}
