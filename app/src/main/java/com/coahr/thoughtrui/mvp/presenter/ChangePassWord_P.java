package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ChangePassWord_C;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.model.ChangePassWord_M;
import com.coahr.thoughtrui.mvp.view.mydata.ChangePasswordFragment;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class ChangePassWord_P extends BasePresenter<ChangePassWord_C.View,ChangePassWord_C.Model> implements ChangePassWord_C.Presenter {
    @Inject
    public ChangePassWord_P(ChangePasswordFragment mview, ChangePassWord_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getChangePass(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getChangePass(map);
        }
    }

    @Override
    public void getChangePassSuccess(ChangePassWord changePassWord) {
        if (getView() != null) {
            getView().getChangePassSuccess(changePassWord);
        }
    }

    @Override
    public void getChangePassFailure(String failure) {
        if (getView() != null) {
            getView().getChangePassFailure(failure);
        }
    }
}
