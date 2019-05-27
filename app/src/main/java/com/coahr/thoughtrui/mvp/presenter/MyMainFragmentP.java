package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.model.MyMainFragmentM;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;


import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:51
 */
public class MyMainFragmentP extends BasePresenter<MyMainFragmentC.View,MyMainFragmentC.Model> implements MyMainFragmentC.Presenter {

  @Inject
    public MyMainFragmentP(MainFragment mview, MyMainFragmentM mModel) {
        super(mview, mModel);
    }

}
