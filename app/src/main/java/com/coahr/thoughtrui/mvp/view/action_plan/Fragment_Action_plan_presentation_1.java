package com.coahr.thoughtrui.mvp.view.action_plan;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
 * 创建日期：2019/4/12
 * 描述：行动计划提报1
 */
public class Fragment_Action_plan_presentation_1 extends BaseFragment {
    @BindView(R.id.plan_1_tittle)
    MyTittleBar plan1Tittle;
    @BindView(R.id.plan_plan_time)
    TextView planPlanTime;
    @BindView(R.id.plan_planer)
    TextView planPlaner;
    @BindView(R.id.plan_province)
    TextView planProvince;
    @BindView(R.id.plan_dealer_name)
    TextView planDealerName;
    @BindView(R.id.plan_templet)
    TextView planTemplet;
    @BindView(R.id.plan_quota_1)
    TextView planQuota1;
    @BindView(R.id.plan_quota_2)
    TextView planQuota2;
    @BindView(R.id.plan_take_photo)
    ImageView planTakePhoto;
    @BindView(R.id.plan_recyclerView)
    RecyclerView planRecyclerView;
    @BindView(R.id.plan_1_next)
    ButtonM plan1Next;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    public static Fragment_Action_plan_presentation_1 newInstance() {
        return new Fragment_Action_plan_presentation_1();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_plan_presentation_1;
    }

    @Override
    public void initView() {
        plan1Next.setFillet(true);
        plan1Next.setRadius(5);
        plan1Next.setBackColor(getResources().getColor(R.color.material_blue_900));
        plan1Next.setTextColor(getResources().getColor(R.color.colorWhite));
        plan1Next.setShape(GradientDrawable.RECTANGLE);
        plan1Tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.plan_province, R.id.plan_dealer_name, R.id.plan_templet, R.id.plan_quota_1, R.id.plan_quota_2, R.id.plan_take_photo, R.id.plan_1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_province:
                break;
            case R.id.plan_dealer_name:
                break;
            case R.id.plan_templet:
                break;
            case R.id.plan_quota_1:
                break;
            case R.id.plan_quota_2:
                break;
            case R.id.plan_take_photo:
                break;
            case R.id.plan_1_next:
                break;
        }
    }

}
