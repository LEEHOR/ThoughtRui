package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:13
 */
public class FragmentTopicsM extends BaseModel<FragmentTopicsC.Presenter> implements FragmentTopicsC.Model {

   @Inject
    public FragmentTopicsM() {
       super();
    }

    @Override
    public void getSubjectList(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<SubjectListBean>(getApiService().getSubjectList(map)))
                .subscribeWith(new SimpleDisposableSubscriber<SubjectListBean>() {
            @Override
            public void _onNext(SubjectListBean SubjectListBean) {
                if (getPresenter() != null) {
                    if (SubjectListBean.getResult()==1) {
                        getPresenter().getSubjectListSuccess(SubjectListBean);
                    }else {
                        getPresenter().getSubjectListFailure(SubjectListBean.getMsg());
                    }
                }
            }
        }));
    }
}
