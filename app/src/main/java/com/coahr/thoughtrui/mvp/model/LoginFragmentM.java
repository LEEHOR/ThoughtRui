package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.LoginFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 21:19
 */
public class LoginFragmentM extends BaseModel<LoginFragmentC.Presenter> implements LoginFragmentC.Model {
  @Inject
    public LoginFragmentM() {

        super();
    }

    @Override
    public void Login(Map map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<LoginBean>(getApiService().getLogin(map))).subscribeWith(new SimpleDisposableSubscriber<LoginBean>() {
            @Override
            public void _onNext(LoginBean loginBean) {
                if (getPresenter() != null) {
                    if (loginBean.getResult()==1) {
                        getPresenter().onLoginSuccess(loginBean);
                    }else {
                        getPresenter().onLoginFailure(loginBean.getMsg());
                    }
                }
            }
        }));
    }
}
