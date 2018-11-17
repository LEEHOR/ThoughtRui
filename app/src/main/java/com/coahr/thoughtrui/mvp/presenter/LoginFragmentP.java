package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.LoginFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.model.LoginFragmentM;
import com.coahr.thoughtrui.mvp.view.mydata.LoginFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 21:22
 */
public class LoginFragmentP extends BasePresenter<LoginFragmentC.View,LoginFragmentC.Model> implements LoginFragmentC.Presenter{
    @Inject
    public LoginFragmentP(LoginFragment mview, LoginFragmentM mModel) {
        super(mview, mModel);
    }

    @Override
    public void Login(Map<String,Object> map) {
        if (mModle != null) {
            mModle.Login(map);
        }
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        if (getView() != null) {
            getView().onLoginSuccess(loginBean);
        }
    }

    @Override
    public void onLoginFailure(String failure) {
        if (getView() != null) {
            getView().onLoginFailure(failure);
        }
    }
}
