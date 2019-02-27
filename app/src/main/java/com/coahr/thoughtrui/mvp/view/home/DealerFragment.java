package com.coahr.thoughtrui.mvp.view.home;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/27
 * 描述：经销商选择页面
 */
public class DealerFragment extends BaseChildFragment {

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static  DealerFragment getInstance() {
        return new DealerFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_dealer_select;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
