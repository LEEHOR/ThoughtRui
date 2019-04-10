package com.coahr.thoughtrui.mvp.view.reviewed;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.model.Bean.Evenbus_Review;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.ReviewStartPagerAdapter;
import com.coahr.thoughtrui.widgets.CustomScrollViewPager;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：审核答题的ViewPager
 */
public class ReviewStartViewPager extends BaseFragment {
    @BindView(R.id.review_start_viewpager)
    CustomScrollViewPager review_start_viewpager;
    @BindView(R.id.review_tittle)
    MyTittleBar myTittleBar;
    private ReviewStartPagerAdapter adapter;
    private String ht_projectId;
    private static final int MSG_1 = 1;
    private ArrayList<String> ht_id_list;
    private int position;
    private String format;

    public static ReviewStartViewPager newInstance(ArrayList<String> ht_id_List, int position, String ht_projectId) {
        ReviewStartViewPager reviewStartViewPager = new ReviewStartViewPager();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ht_id_list", ht_id_List);
        bundle.putInt("position", position);
        bundle.putString("ht_projectId", ht_projectId);
        reviewStartViewPager.setArguments(bundle);
        return reviewStartViewPager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                setViewPager();
            }
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.fragment_review_startproject;
    }

    @Override
    public void initView() {
        review_start_viewpager.setScrollable(false);
        review_start_viewpager.setOffscreenPageLimit(1);
        myTittleBar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_8));
            }
        });
    }

    @Override
    public void initData() {
        format = getResources().getString(R.string.start_activity_tittle);
        if (getArguments() != null) {
            ht_id_list = getArguments().getStringArrayList("ht_id_list");
            position = getArguments().getInt("position");
            ht_projectId = getArguments().getString("ht_projectId");
            setViewPager();
        }
        String format1 = String.format(format, 1);
        myTittleBar.getTvTittle().setText(format1);
    }

    /**
     * 翻页获取返回询问数据
     *
     * @param isCompleteBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(isCompleteBean isCompleteBean) {
        int isposition = isCompleteBean.getPosition();
        int isupOrDown = isCompleteBean.getUpOrDown();
        int type = isCompleteBean.getType();
        boolean complete = isCompleteBean.isComplete();
        if (type == 2) {  //审核页面
            if (complete) {
                if (isupOrDown == 1) {  //上翻页
                    if (isposition > 0) {
                        review_start_viewpager.setCurrentItem(review_start_viewpager.getCurrentItem() - 1, true);
                        String format = String.format(this.format, isposition);
                        myTittleBar.getTvTittle().setText(format);
                    }
                }
                if (isupOrDown == 2) {
                    review_start_viewpager.setCurrentItem(review_start_viewpager.getCurrentItem() + 1);
                    String format = String.format(this.format, isposition);
                    myTittleBar.getTvTittle().setText(format);

                }
            } else {
                ToastUtils.showLong(getResources().getString(R.string.toast_31));
            }
        }
    }

    /**
     * 审核列表过来
     *
     * @param evenbus_review
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EventInfoList(Evenbus_Review evenbus_review) {

    }

    private void setViewPager() {
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", ht_projectId);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                String format = String.format(this.format, subjectsDBS.get(0).getNumber());
                myTittleBar.getTvTittle().setText(format);
            }
            List<SubjectsDB> subjectsDBList = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBList != null && subjectsDBList.size() > 0) {
                adapter = new ReviewStartPagerAdapter(getChildFragmentManager(), String.valueOf(projectsDBS.get(0).getId()), ht_projectId, ht_id_list, ht_id_list.size());
                review_start_viewpager.setAdapter(adapter);
                review_start_viewpager.setCurrentItem(position);
            } else {
                ToastUtils.showLong(getResources().getString(R.string.phrases_9));
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                _mActivity.onBackPressed();
            }
        }).build().show();
    }
}
