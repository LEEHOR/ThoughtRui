package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.MyFragment_C;
import com.coahr.thoughtrui.mvp.model.MyFragment_M;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class MyFragment_P extends BasePresenter<MyFragment_C.View,MyFragment_C.Model> implements MyFragment_C.Presenter {
    @Inject
    public MyFragment_P(MyFragment_C.View mview, MyFragment_M mModel) {
        super(mview, mModel);
    }
}
