package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ReviewPagerFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：1.0.0
 * 创建日期：2019/1/7
 * 描述：
 */
public class ReviewPagerFragment_M extends BaseModel<ReviewPagerFragment_C.Presenter> implements ReviewPagerFragment_C.Model {
    @Inject
    public ReviewPagerFragment_M() {
        super();
    }

    @Override
    public void getCensorList(Map<String, Object> map) {
            mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<CensorBean>(getApiService().getCensorList(map)))
            .subscribeWith(new SimpleDisposableSubscriber<CensorBean>() {
                @Override
                public void _onNext(CensorBean censorBean) {
                    if (getPresenter() != null) {
                        if (censorBean.getResult()==1) {
                            getPresenter().getCensorListSuccess(censorBean);
                        }else {
                            getPresenter().getCensorListFailure(censorBean.getMsg(),censorBean.getResult());
                        }
                    }
                }
            }));
    }
}
