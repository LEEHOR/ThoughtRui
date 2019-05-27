package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 19:34
 */
public interface LoginFragmentC {
    interface View extends BaseContract.View {

        void onLoginSuccess(LoginBean loginBean);

        void onLoginFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void Login(Map<String,Object> map);

        void onLoginSuccess(LoginBean loginBean);

        void onLoginFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void Login(Map<String,Object> map);

    }
}
