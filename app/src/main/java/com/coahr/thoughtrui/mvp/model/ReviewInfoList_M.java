package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C.Model;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewInfoList_M extends BaseModel<ReviewInfoList_C.Presenter> implements ReviewInfoList_C.Model {
    @Inject
    public ReviewInfoList_M() {
        super();
    }

    @Override
    public void getCensorInfoList(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<CensorInfoList>(getApiService().getCensorInfoList(map)))
                .subscribeWith(new SimpleDisposableSubscriber<CensorInfoList>() {
                    @Override
                    public void _onNext(CensorInfoList censorInfoList) {
                        if (getPresenter() != null) {
                            if (censorInfoList.getResult()==1) {
                                getPresenter().getCensorInfoListSuccess(censorInfoList);
                            }else {
                                getPresenter().getCensorInfoListFailure(censorInfoList.getMsg());
                            }
                        }
                    }
                }));
    }
}
