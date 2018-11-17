package com.coahr.thoughtrui.mvp.view.upload;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.presenter.UploadP;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:12
 */
public class UploadFragment extends BaseFragment<UploadC.Presenter> implements UploadC.View {
    @Inject
    UploadP p;
    @Override
    public UploadC.Presenter getPresenter() {
        return p;
    }
    public static UploadFragment newInstance() {
        return new UploadFragment();
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_upload;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLocationSuccess(BDLocation location) {

    }

    @Override
    public void onLocationFailure(int failure) {

    }

    @Override
    public void getHomeDataSuccess() {

    }

    @Override
    public void getHomeDataFailure(String fail) {

    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void dismissLoading() {

    }
}
