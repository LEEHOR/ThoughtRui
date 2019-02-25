package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;

import androidx.cardview.widget.CardView;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：首页选择页面
 */
public class MainInfoFragment extends BaseChildFragment {
    @BindView(R.id.card_histore)
    CardView card_histore;
    @BindView(R.id.card_start)
    CardView card_start;
    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }
    public static MainInfoFragment newInstance() {
        return new MainInfoFragment();
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_info;
    }

    @Override
    public void initView() {
        card_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToProjectTemplate();
            }
        });

        card_histore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToProjectList();
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 跳转到项目附件
     */
    private void JumpToProjectList(){
        Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_main);
        startActivity(intent);
    }

    /**
     * 跳转到项目模板
     */
    private void JumpToProjectTemplate(){
        Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_template);
        startActivity(intent);
    }
}
