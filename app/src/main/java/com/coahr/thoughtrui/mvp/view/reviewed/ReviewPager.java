package com.coahr.thoughtrui.mvp.view.reviewed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.ReviewPagerFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_censor;
import com.coahr.thoughtrui.mvp.presenter.ReviewPagerFragment_P;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.pageAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：已通过或未通过页面
 */
public class ReviewPager extends BaseChildFragment<ReviewPagerFragment_C.Presenter> implements ReviewPagerFragment_C.View {
    @Inject
    ReviewPagerFragment_P p;
    @BindView(R.id.pager_recycler)
    RecyclerView pager_recycler;
    @BindView(R.id.pager_swipe)
    SwipeRefreshLayout pager_swipe;
    private boolean isLoading;
    private pageAdapter adapter;
    private LinearLayoutManager manager;
    private int type;

    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
    }

    public static ReviewPager newInstance(int type) {
        ReviewPager reviewPager = new ReviewPager();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        reviewPager.setArguments(bundle);
        return reviewPager;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_pager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        adapter = new pageAdapter();
        manager = new LinearLayoutManager(BaseApplication.mContext);
        pager_recycler.setAdapter(adapter);
       // pager_recycler.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5), getResources().getColor(R.color.material_grey_200)));
      /*  for (int i = 0; i < pager_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                pager_recycler.removeItemDecorationAt(i);
            }
        }*/
        pager_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    getDataList(null);
                } else {
                    pager_swipe.setRefreshing(false);
                }
            }
        });
        adapter.setOnItemClick(new pageAdapter.OnItemClick() {
            @Override
            public void OnClick(CensorBean.DataBean.ListBean bean, int position) {
                    start(ReviewInfoList.newInstance(bean.getId(),Constants.sessionId,type));
            }
        });
    }

    @Override
    public void initData() {
        getDataList(null);
    }

    @Override
    public void getCensorListSuccess(CensorBean censorBean) {
        adapter.setNewData(censorBean.getData().getList());
        isLoading = false;
        pager_swipe.setRefreshing(false);
    }

    @Override
    public void getCensorListFailure(String failure) {
        isLoading = false;
        pager_swipe.setRefreshing(false);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
        isLoading = false;
        pager_swipe.setRefreshing(false);
    }

    /**
     * 联网
     *
     * @param search 查询
     */
    private void getDataList(String search) {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("status", String.valueOf(type == 0 ? 1 : -1));
        if (search != null) {
            map.put("search", search);
        }
        p.getCensorList(map);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(EvenBus_censor messageEvent) {
        if (type == messageEvent.getType()) {
            getDataList(messageEvent.getInputText());
        }
    }
}
