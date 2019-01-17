package com.coahr.thoughtrui.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MainActivityC;
import com.coahr.thoughtrui.mvp.model.MainActivityM;
import com.coahr.thoughtrui.mvp.view.MainActivity;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class MainActivityP extends BasePresenter<MainActivityC.View,MainActivityC.Model> implements MainActivityC.Presenter
{
    @Inject
    public MainActivityP(MainActivity mview, MainActivityM mModel) {
        super(mview, mModel);
    }

}
