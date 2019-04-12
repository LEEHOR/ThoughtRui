package com.coahr.thoughtrui.mvp.view.action_plan;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.landptf.view.ButtonM;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/11
 * 描述：行动计划提报首页
 */
public class Fragment_action_projects extends BaseFragment {
    @BindView(R.id.plan_tittle)
    MyTittleBar planTittle;
    @BindView(R.id.plan_recycler)
    RecyclerView planRecycler;
    @BindView(R.id.plan_top_line)
    View planTopLine;
    @BindView(R.id.plan_action_pre)
    ButtonM planActionPre;
    @BindView(R.id.plan_bottom_parts)
    RelativeLayout planBottomParts;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fragment_action_projects newInstance() {
        return new Fragment_action_projects();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_projects;
    }

    @Override
    public void initView() {
        planActionPre.setFillet(true);
        planActionPre.setRadius(30);
        planActionPre.setBackColor(getResources().getColor(R.color.material_blue_700));
        planActionPre.setTextColor(getResources().getColor(R.color.colorWhite));
        planActionPre.setShape(GradientDrawable.OVAL);
        planTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.plan_action_pre)  //填写行动计划报表
    public void onViewClicked() {
        start(Fragment_Action_plan_presentation_1.newInstance());
    }
}
