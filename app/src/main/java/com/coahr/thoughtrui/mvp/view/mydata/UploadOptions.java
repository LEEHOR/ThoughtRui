package com.coahr.thoughtrui.mvp.view.mydata;

import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/12
 * 描述：数据上传设置
 */
public class UploadOptions extends BaseFragment {
    @BindView(R.id.upload_options_tittle)
    MyTittleBar upload_options_tittle;
    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static UploadOptions newInstance() {
        return new UploadOptions();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_upload_options;
    }

    @Override
    public void initView() {
        upload_options_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }
}
