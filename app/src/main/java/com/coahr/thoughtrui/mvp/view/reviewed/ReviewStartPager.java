package com.coahr.thoughtrui.mvp.view.reviewed;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
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
 * 描述：修改答案ViewPager
 */
public class ReviewStartPager extends BaseFragment {
    @BindView(R.id.review_start_viewpager)
    CustomScrollViewPager review_start_viewpager;
    @BindView(R.id.review_tittle)
    MyTittleBar myTittleBar;
    private ReviewStartPagerAdapter adapter;
    private String ht_projectId;
    private static final int MSG_1 = 1;
    private ArrayList<String> ht_id_list;
    private int position;
    public static ReviewStartPager newInstance(ArrayList<String> ht_id_List,int position,String ht_projectId) {
        ReviewStartPager reviewStartPager = new ReviewStartPager();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("ht_id_list",ht_id_List);
        bundle.putInt("position",position);
        bundle.putString("ht_projectId",ht_projectId);
        reviewStartPager.setArguments(bundle);
        return reviewStartPager;
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
        myTittleBar.getTvTittle().setText("第一题");
        review_start_viewpager.setScrollable(false);
        myTittleBar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            ht_id_list = getArguments().getStringArrayList("ht_id_list");
            position = getArguments().getInt("position");
            ht_projectId= getArguments().getString("ht_projectId");
            setViewPager();
        }
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
        boolean complete = isCompleteBean.isComplete();

        if (complete) {
            if (isupOrDown == 1) {  //上翻页
                KLog.d("上翻页" + isposition);
                if (isposition > 0) {
                    review_start_viewpager.setCurrentItem(review_start_viewpager.getCurrentItem() - 1, true);
                    myTittleBar.getTvTittle().setText("第" + (isposition) + "题");
                }
            }
            if (isupOrDown == 2) {
                KLog.d("下翻页" + isposition);
                review_start_viewpager.setCurrentItem(review_start_viewpager.getCurrentItem() + 1);
                myTittleBar.getTvTittle().setText("第" + (isposition) + "题");

            }
        } else {
            ToastUtils.showLong("当前题目未完成");
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
            List<SubjectsDB> subjectsDBList = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBList != null && subjectsDBList.size()>0) {
                adapter = new ReviewStartPagerAdapter(getChildFragmentManager(), ht_id_list, ht_id_list.size(), String.valueOf(projectsDBS.get(0).getId()), ht_projectId);
                review_start_viewpager.setAdapter(adapter);
                review_start_viewpager.setCurrentItem(position);
            } else {
               ToastUtils.showLong("当前项目下没有题目");
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
}
