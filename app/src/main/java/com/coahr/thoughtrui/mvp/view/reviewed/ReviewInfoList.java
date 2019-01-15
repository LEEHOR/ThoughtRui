package com.coahr.thoughtrui.mvp.view.reviewed;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ReviewInfoList_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
import com.coahr.thoughtrui.mvp.presenter.ReviewInfoList_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.ReviewInfoListAdapter;

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
    private ReviewInfoListAdapter adapter;
    private LinearLayoutManager manager;
    private String projectId;
    private String sessionId;
    private int statues;
    private List<CensorInfoList.DataBean.ListBean> list;
    private ArrayList<String> targetList=new ArrayList<>();

    public static ReviewInfoList newInstance(String projectId,String sessionId,int statues) {
        ReviewInfoList infoList=new ReviewInfoList();
        Bundle bundle=new Bundle();
        bundle.putString("projectId",projectId);
        bundle.putString("sessionId",sessionId);
        bundle.putInt("statues",statues);
        infoList.setArguments(bundle);
        return infoList;
    }

    @Override
    public ReviewInfoList_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_pager;
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
        pager_recycler.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5), getResources().getColor(R.color.material_grey_200)));
        for (int i = 0; i < pager_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                pager_recycler.removeItemDecorationAt(i);
            }
        }
        getInfoList();
        adapter.setListener(new ReviewInfoListAdapter.OnClickListener() {
            @Override
            public void OnClick(CensorInfoList.DataBean.ListBean bean,int position) {
                if (list!= null && list.size()>0) {
                    targetList.clear();
                    for (int i = 0; i <list.size() ; i++) {
                        targetList.add(list.get(i).getId());
                    }
                    start(ReviewStartPager.newInstance( targetList,position,projectId));
                }
            }
        });
    }

    @Override
    public void getCensorInfoListSuccess(CensorInfoList censorInfoList) {
        if (censorInfoList.getData() != null) {
            list = censorInfoList.getData().getList();
            if (list!= null && list.size()>0) {
                adapter.setNewData(censorInfoList.getData().getList());
            }
        }

    }

    @Override
    public void getCensorInfoListFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    private void getInfoList(){
        Map map=new HashMap();
        map.put("projectId",projectId);
        map.put("status",String.valueOf(statues+1));
        p.getCensorInfoList(map);
    }

}
