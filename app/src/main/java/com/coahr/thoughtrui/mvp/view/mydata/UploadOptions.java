package com.coahr.thoughtrui.mvp.view.mydata;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

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
    @BindView(R.id.cbWifiUpload)
    CheckBox cbWifiUpload;

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
        cbWifiUpload.setChecked(PreferenceUtils.getPrefBoolean(_mActivity, Constants.WIFI_CONNECTED_UPLOAD_KEY, false));
        cbWifiUpload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtils.setPrefBoolean(_mActivity, Constants.WIFI_CONNECTED_UPLOAD_KEY, isChecked);
            }
        });
    }
}
