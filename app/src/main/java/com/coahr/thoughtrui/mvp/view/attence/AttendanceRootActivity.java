package com.coahr.thoughtrui.mvp.view.attence;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Attend;
import com.coahr.thoughtrui.widgets.SelectImageView;
import com.coahr.thoughtrui.widgets.SelectTextView;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:05
 */
public class AttendanceRootActivity extends BaseSupportActivity {
    @BindView(R.id.top_line)
    LinearLayout top_line;
    @BindView(R.id.myTitle)
    MyTittleBar myTitle;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.project_code)
    TextView project_code;
    @BindView(R.id.project_company_name)
    TextView project_company_name;
    @BindView(R.id.project_company_address)
    TextView project_company_address;
    @BindView(R.id.left_lin)
    LinearLayout left_lin;
    @BindView(R.id.right_lin)
    LinearLayout right_lin;
    @BindView(R.id.iv_select_k)
    SelectImageView iv_select_k;
    @BindView(R.id.iv_select_h)
    SelectImageView iv_select_h;

    @BindView(R.id.tv_select_k)
    SelectTextView tv_select_k;

    @BindView(R.id.tv_select_h)
    SelectTextView tv_select_h;
    private int bottomNavigationPreposition=0;

    private SupportFragment[] mFragments = new SupportFragment[2];

    /**
     * 获取数据
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(Event_Attend messageEvent) {

    }
    @Override
    public int binLayout() {
        return R.layout.activity_attendanceroot;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFragments[0] = findFragment(AttendanceFragment_k.class);
           mFragments[1] = findFragment(AttendanceFragment_h.class);
        } else {
            mFragments[0] = AttendanceFragment_k.newInstance();
            mFragments[1] = AttendanceFragment_h.newInstance();
        }
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void initView() {
        top_line.setPadding(top_line.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), top_line.getPaddingRight(), top_line.getPaddingBottom());

        left_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_select_k.toggle(false);
                iv_select_h.toggle(true);

                tv_select_k.toggle(false);
                tv_select_h.toggle(true);
                showFragment(0);
            }
        });

        right_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_select_k.toggle(true);
                iv_select_h.toggle(false);

                tv_select_k.toggle(true);
                tv_select_h.toggle(false);
                showFragment(1);
            }
        });
    }

    @Override
    public void initData() {
        iv_select_k.toggle(false);
        tv_select_k.toggle(false);
        loadMultipleRootFragment(R.id.attachment_root, 0, mFragments);
        showFragment(0);
    }

    private void showFragment(int position) {
        showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
        bottomNavigationPreposition = position;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
