package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:49
 */
public class MyMainFragmentM extends BaseModel<MyMainFragmentC.Presenter> implements MyMainFragmentC.Model {

    @Inject
    public MyMainFragmentM() {
        super();
    }

}
