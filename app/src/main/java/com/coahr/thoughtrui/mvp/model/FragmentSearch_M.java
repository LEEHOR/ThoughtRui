package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.FragmentSearch_c;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class FragmentSearch_M extends BaseModel<FragmentSearch_c.Presenter> implements FragmentSearch_c.Model {
    @Inject
    public FragmentSearch_M() {
        super();
    }

    @Override
    public void getSearch(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<SearchBeans>(getApiService().search(map)))
                .subscribeWith(new SimpleDisposableSubscriber<SearchBeans>() {
                    @Override
                    public void _onNext(SearchBeans searchBeans) {
                        if (getPresenter() != null) {
                            if (searchBeans.getResult()==1) {
                                getPresenter().getSearchSuccess(searchBeans);
                            }else {
                                getPresenter().getSearchFailure(searchBeans.getMsg());
                            }
                        }
                    }
                }));
    }
}
