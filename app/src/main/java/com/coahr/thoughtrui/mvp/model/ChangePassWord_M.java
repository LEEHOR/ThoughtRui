package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ChangePassWord_C;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class ChangePassWord_M extends BaseModel<ChangePassWord_C.Presenter> implements ChangePassWord_C.Model {
    @Inject
    public ChangePassWord_M() {
        super();
    }

    @Override
    public void getChangePass(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<ChangePassWord>(getApiService().getChangePassWord(map)))
                .subscribeWith(new SimpleDisposableSubscriber<ChangePassWord>() {
                    @Override
                    public void _onNext(ChangePassWord changePassWord) {
                        if (getPresenter() != null) {
                            if (changePassWord.getResult()==1) {
                                getPresenter().getChangePassSuccess(changePassWord);
                            }else {
                                getPresenter().getChangePassFailure(changePassWord.getMsg());
                            }
                        }
                    }
                }));
    }
}
