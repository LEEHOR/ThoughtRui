package com.coahr.thoughtrui.mvp.view.action_plan;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_viewPager_c;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_report;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_viewPager_P;
import com.coahr.thoughtrui.mvp.view.action_plan.Adapter.item_plan_viewpager_adapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/15
 * 描述：
 */
public class Fragment_action_plan_viewPager extends BaseChildFragment<Fragment_action_plan_viewPager_c.Presenter> implements Fragment_action_plan_viewPager_c.View {
    @Inject
    Fragment_action_plan_viewPager_P p;
    @BindView(R.id.plan_viewpager_recycler)
    RecyclerView planViewpagerRecycler;
    private int position;
    private ArrayList<ReportList.DataBean.AllListBean> reportList_s = new ArrayList<>();
    private item_plan_viewpager_adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View empty;
    private View error;
    private boolean submitStatus;

    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
    }

    public static Fragment_action_plan_viewPager newInstance(int position) {
        Fragment_action_plan_viewPager plan_viewPager = new Fragment_action_plan_viewPager();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        plan_viewPager.setArguments(bundle);
        return plan_viewPager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_plan_viewpager;
    }

    @Override
    public void initView() {
        getReportList();
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        adapter = new item_plan_viewpager_adapter(reportList_s);
        linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        planViewpagerRecycler.setAdapter(adapter);
        planViewpagerRecycler.setLayoutManager(linearLayoutManager);
        empty = getLayoutInflater().inflate(R.layout.recycler_empty_view, (ViewGroup) planViewpagerRecycler.getParent(), false);
        error = getLayoutInflater().inflate(R.layout.recycler_error_view, (ViewGroup) planViewpagerRecycler.getParent(), false);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReportList();
            }
        });
        /*error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReportList();
            }
        });*/

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReportList.DataBean.AllListBean allListBean = (ReportList.DataBean.AllListBean) adapter.getItem(position);
                ((SupportFragment) getParentFragment()).start(Fragment_Action_plan_presentation_1.newInstance(allListBean, 2));
            }
        });
    }

    /**
     * Evenbus,提交后，自动关闭
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EvenBus(Boolean isFinish) {
        KLog.e("测试代码", "isFinish == " + isFinish);
        if (isFinish) {
            getReportList();
        }
    }

    @Override
    public void getPlanListSuccess(ReportList reportList) {
        if (reportList.getData() != null) {
            submitStatus = reportList.getData().isSubmitStatus();
            EventBus.getDefault().postSticky(new EvenBus_report(submitStatus));
        }
        reportList_s.clear();
        if (position == 0) {  //未完成
            if (reportList != null) {
                if (reportList.getData() != null) {
                    if (reportList.getData().getAllList() != null && reportList.getData().getAllList().size() > 0) {
                        KLog.e("测试代码", "size == " + reportList.getData().getAllList().size());
                        for (int i = 0; i < reportList.getData().getAllList().size(); i++) {
                            KLog.e("测试代码", "CompleteStatus == " + reportList.getData().getAllList().get(i).getCompleteStatus());
                            if (reportList.getData().getAllList().get(i).getCompleteStatus() == -1) {
                                reportList_s.add(reportList.getData().getAllList().get(i));
                            }
                        }
                    }
                }
            }
        } else if (position == 1) { //已完成
            if (reportList != null) {
                if (reportList.getData() != null) {
                    if (reportList.getData().getAllList() != null && reportList.getData().getAllList().size() > 0) {
                        KLog.e("测试代码", "size == " + reportList.getData().getAllList().size());
                        for (int i = 0; i < reportList.getData().getAllList().size(); i++) {
                            KLog.e("测试代码", "CompleteStatus == " + reportList.getData().getAllList().get(i).getCompleteStatus());
                            if (reportList.getData().getAllList().get(i).getCompleteStatus() == 1) {
                                reportList_s.add(reportList.getData().getAllList().get(i));
                            }
                        }
                    }
                }
            }
        } else {
            if (reportList != null) {
                if (reportList.getData() != null) {
                    if (reportList.getData().getAllList() != null && reportList.getData().getAllList().size() > 0) {
                        reportList_s.addAll(reportList.getData().getAllList());
                    }
                }
            }
        }
        if (reportList_s.size() > 0) {
            adapter.setNewData(reportList_s);
        } else {
            adapter.setEmptyView(empty);
        }

    }

    @Override
    public void getPlanListFailure(String failure) {
        ToastUtils.showLong(failure);
        adapter.setEmptyView(error);
    }

    /**
     * 获取提报列表
     */
    private void getReportList() {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        KLog.e("测试代码", "sessionId == " + Constants.sessionId);
        p.getPlanList(map);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        adapter.setEmptyView(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
