package com.coahr.thoughtrui.mvp.view.projectAnnex;

import android.os.Bundle;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：项目附件子页
 */
public class FragmentAnnex extends BaseChildFragment {

    public static FragmentAnnex newInstance(int position,String projectId) {
        FragmentAnnex annex=new FragmentAnnex();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString("projectId",projectId);
        annex.setArguments(bundle);
        return  annex;
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_annex;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
