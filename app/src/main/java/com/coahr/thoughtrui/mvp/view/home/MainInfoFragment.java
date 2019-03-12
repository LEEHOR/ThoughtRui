package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.presenter.MainInfoFragment_P;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：首页选择页面
 */
public class MainInfoFragment extends BaseChildFragment<MainInfoFragment_C.Presenter> implements  MainInfoFragment_C.View {
   @Inject
    MainInfoFragment_P p;
    @BindView(R.id.card_histore)
    CardView card_histore;
    @BindView(R.id.card_start)
    CardView card_start;
    @BindView(R.id.iv_project_tag)
    ImageView iv_project_tag;
    @BindView(R.id.iv_massage_center)
    ImageView iv_massage_center;
    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
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
        iv_massage_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpToMassage();
            }
        });

    }

    @Override
    public void initData() {
        p.getProject(Constants.sessionId);
    }

    /**
     * 跳转到历史项目
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
       // intent.putExtra("url","http://www.baidu.com");
        intent.putExtra("to", Constants.fragment_template);
        startActivity(intent);
    }

    /**
     * 跳转到消息中心
     */
    private void JumpToMassage(){
        Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        // intent.putExtra("url","http://www.baidu.com");
        intent.putExtra("to", Constants.fragment_uploadOptions);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getProjectSuccess() {
        iv_project_tag.setVisibility(View.VISIBLE);
    }

    @Override
    public void getProjectFailure(String failure) {
        iv_project_tag.setVisibility(View.INVISIBLE);
    }

}
