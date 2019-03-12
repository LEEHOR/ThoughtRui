package com.coahr.thoughtrui.mvp.view.UMPush;

import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：推送列表
 */
public class Fragment_Umeng extends BaseFragment {
    @BindView(R.id.umeng_tittle)
    MyTittleBar umeng_tittle;
    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fragment_Umeng newInstance() {
        return new Fragment_Umeng();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_umeng;
    }

    @Override
    public void initView() {
        umeng_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
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
