package com.coahr.thoughtrui.mvp.view.mydata;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.presenter.Feedback_Fragment_P;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/22
 * 描述：意见反馈
 */
public class Fragment_Feedback extends BaseFragment<Feedback_Fragment_C.Presenter> implements Feedback_Fragment_C.View{
    @Inject
    Feedback_Fragment_P p;
    @Override
    public Feedback_Fragment_C.Presenter getPresenter() {
        return p;
    }

    public static Fragment_Feedback newInstance() {
        return new Fragment_Feedback();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_feed_back;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void sendSuggestionSuccess(FeedBack feedBack) {

    }

    @Override
    public void sendSuggestionFailure(String failure,int code) {

    }
}
