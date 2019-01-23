package com.coahr.thoughtrui.mvp.view.reviewed;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
import com.coahr.thoughtrui.mvp.presenter.ReviewInfoList_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.ReviewInfoListAdapter;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：审核详情页
 */
public class ReviewInfoList extends BaseFragment<ReviewInfoList_C.Presenter> implements ReviewInfoList_C.View {


    @Inject
    ReviewInfoList_P p;
    @BindView(R.id.pager_recycler)
    RecyclerView pager_recycler;
    @BindView(R.id.review_title)
    MyTittleBar review_title;
    @BindView(R.id.pagerInfoList_swipe)
    SwipeRefreshLayout pagerInfoList_swipe;
    private boolean isLoading;
    private ReviewInfoListAdapter adapter;
    private LinearLayoutManager manager;
    private String projectId;
    private String sessionId;
    private int statues;
    private List<CensorInfoList.DataBean.ListBean> list;
    private ArrayList<String> targetList = new ArrayList<>();
    private boolean isHavePermission = false;

    public static ReviewInfoList newInstance(String projectId, String sessionId, int statues) {
        ReviewInfoList infoList = new ReviewInfoList();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", projectId);
        bundle.putString("sessionId", sessionId);
        bundle.putInt("statues", statues);
        infoList.setArguments(bundle);
        return infoList;
    }

    @Override
    public ReviewInfoList_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_pagerinfo_list;
    }

    @Override
    public void initView() {
        adapter = new ReviewInfoListAdapter();
        manager = new LinearLayoutManager(BaseApplication.mContext);
        pager_recycler.setLayoutManager(manager);
        pager_recycler.setAdapter(adapter);
        if (getArguments() != null) {
            projectId = getArguments().getString("projectId");
            sessionId = getArguments().getString("sessionId");
            statues = getArguments().getInt("statues");
        }
    }

    @Override
    public void initData() {
        pager_recycler.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 10)));
        for (int i = 0; i < pager_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                pager_recycler.removeItemDecorationAt(i);
            }
        }
        getInfoList();
        adapter.setListener(new ReviewInfoListAdapter.OnClickListener() {
            @Override
            public void OnClick(CensorInfoList.DataBean.ListBean bean, int position) {
                if (getAudioPermission()) {
                    if (list != null && list.size() > 0) {
                        targetList.clear();
                        for (int i = 0; i < list.size(); i++) {
                            targetList.add(list.get(i).getId());
                        }
                        ToastUtils.showLong("暂未完成，敬请期待");
                     //   start(ReviewStartPager.newInstance(targetList, position, projectId));
                        //  EventBus.getDefault().postSticky(new Evenbus_Review(projectId,position,targetList));
                    }
                }

            }
        });
        review_title.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        review_title.getTvTittle().setText(statues == 0 ? "审核未通过" : statues == 1 ? "审核通过" : "审核未通过");
        pagerInfoList_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    getInfoList();
                } else {
                    pagerInfoList_swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void getCensorInfoListSuccess(CensorInfoList censorInfoList) {
        if (censorInfoList.getData() != null) {
            list = censorInfoList.getData().getList();
            if (list != null && list.size() > 0) {
                adapter.setTag(statues);
                adapter.setNewData(censorInfoList.getData().getList());
            }
        }

        isLoading = false;
        pagerInfoList_swipe.setRefreshing(false);

    }

    @Override
    public void getCensorInfoListFailure(String failure) {
        ToastUtils.showLong(failure);
        isLoading = false;
        pagerInfoList_swipe.setRefreshing(false);
    }

    private void getInfoList() {
        Map map = new HashMap();
        map.put("projectId", projectId);
        map.put("status", statues == 0 ? String.valueOf(-1) : statues == 1 ? String.valueOf(1) : String.valueOf(-1));
        p.getCensorInfoList(map);
    }

    /*    *
     * 动态获取录音权限
     */
    private boolean getAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                            isHavePermission = false;
                            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            ToastUtils.showLong("获取" + permissions.get(0) + "失败");
                            isHavePermission = false;
                        }

                        @Override
                        public void PermissionHave() {
                            isHavePermission = true;
                            //EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }
                    }, Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {
            isHavePermission = true;
            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
        }
        return isHavePermission;
    }
}
