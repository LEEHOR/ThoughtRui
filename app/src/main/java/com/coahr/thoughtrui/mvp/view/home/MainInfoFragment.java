package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment_not_padding;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
import com.coahr.thoughtrui.mvp.presenter.MainInfoFragment_P;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：首页选择页面
 */
public class MainInfoFragment extends BaseFragment_not_padding<MainInfoFragment_C.Presenter> implements MainInfoFragment_C.View{
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

    private boolean isLoaded = false;

    public static MainInfoFragment newInstance() {
        return new MainInfoFragment();
    }

    @Override
    public MainInfoFragment_C.Presenter getPresenter() {
        return null;
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

        /*KLog.e("测试代码", "messageNum == " + PreferenceUtils.getPrefInt(_mActivity, Constants.messageNum, 0));
        iv_massage_center.setImageResource(PreferenceUtils.getPrefInt(_mActivity, Constants.messageNum, 0) > 0 ? R.mipmap.news : R.mipmap.no_news);*/
    }

    @Override
    public void initData() {
        p.getProject(Constants.sessionId);
        getDateBy_net();
    }

    private void getDateBy_net() {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("status", -1);
        p.getNotification_net(map);
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.e("测试代码", "isLoaded == " + isLoaded);
        if (!isLoaded) {
            isLoaded = true;
        } else {
            initData();
        }
    }

    /**
     * 跳转到历史项目
     */
    private void JumpToProjectList() {
        Intent intent = new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_main);
        startActivity(intent);
    }

    /**
     * 跳转到项目模板
     */
    private void JumpToProjectTemplate() {
        Intent intent = new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_template);
        startActivity(intent);
    }

    /**
     * 跳转到消息中心
     */
    private void JumpToMassage() {
        Intent intent = new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
        intent.putExtra("to", Constants.fragment_umeng);
        // intent.putExtra("url","http://61.183.131.58:8080/DFL_KM_GATEWAY/avicit/www/index.html?username_bGl3eA==");
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

    @Override
    public void getNotification_DbSuccess(NotificationBean notificationBean, int notificationNum) {
        Constants.notificationNum += notificationNum;
        KLog.e("测试代码", "notificationNum == " + notificationNum + " -- Constants.notificationNum == " + Constants.notificationNum);
        PreferenceUtils.setPrefInt(_mActivity, Constants.messageNum, notificationNum);
        KLog.e("测试代码", "messageNum == " + PreferenceUtils.getPrefInt(_mActivity, Constants.messageNum, 0));
        iv_massage_center.setImageResource(Constants.notificationNum > 0 ? R.mipmap.news : R.mipmap.no_news);
    }

    @Override
    public void getNotification_DbFailure(String failure) {
        PreferenceUtils.setPrefInt(_mActivity, Constants.messageNum, Constants.notificationNum);
        KLog.e("测试代码", "messageNum == " + PreferenceUtils.getPrefInt(_mActivity, Constants.messageNum, 0));
        iv_massage_center.setImageResource(Constants.notificationNum > 0 ? R.mipmap.news : R.mipmap.no_news);
    }

    @Override
    public void getNotification_netSuccess(CensorBean censorBean) {
        Constants.notificationNum = 0;
        if (censorBean != null && censorBean.getData().getList() != null && censorBean.getData().getList().size() > 0) {
            for (int i = 0; i < censorBean.getData().getList().size(); i++) {
                Constants.notificationNum++;
            }
        }

        p.getNotification_Db(Constants.sessionId);
    }

    @Override
    public void getNotification_netFailure(String failure) {

    }
}
