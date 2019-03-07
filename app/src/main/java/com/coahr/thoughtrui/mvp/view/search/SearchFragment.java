package com.coahr.thoughtrui.mvp.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentSearch_c;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;
import com.coahr.thoughtrui.mvp.presenter.FragmentSearch_P;
import com.coahr.thoughtrui.mvp.view.home.ProjectDetailFragment;
import com.coahr.thoughtrui.mvp.view.search.adapter.SearchAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/7
 * 描述：搜索页面
 */
public class SearchFragment extends BaseFragment<FragmentSearch_c.Presenter> implements FragmentSearch_c.View {
    @Inject
    FragmentSearch_P p;
    @BindView(R.id.re_search_2)
    RelativeLayout re_search;
    @BindView(R.id.ed_search_2)
    EditText ed_search;
    @BindView(R.id.search_cancel_2)
    TextView search_cancel;
    @BindView(R.id.search_recy)
    RecyclerView search_recy;
    private int type;
    private LinearLayoutManager manager;
    private SearchAdapter adapter;

    @Override
    public FragmentSearch_c.Presenter getPresenter() {
        return p;
    }

    /**
     * 实例化
     *
     * @param type 搜索类型
     *             type= 1.首页
     *             type= 2.审核
     * @return
     */
    public static SearchFragment instance(int type) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }

    }

    @Override
    public void initData() {
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_search.setCursorVisible(false);
                ed_search.setFocusable(false);
                ed_search.setFocusableInTouchMode(false);
                search_cancel.setVisibility(View.GONE);
            }
        });
        ed_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                search_cancel.setVisibility(View.VISIBLE);
                ed_search.setFocusable(true);
                ed_search.setCursorVisible(true);
                ed_search.setFocusableInTouchMode(true);
                return false;
            }
        });

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(ed_search.getText()) || ed_search.getText().toString().equals("")) {

                    } else {
                        getSearch(ed_search.getText().toString().trim());

                    }
                    return true;
                }
                return false;
            }
        });

        search_recy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_search.setCursorVisible(false);
                ed_search.setFocusable(false);
                ed_search.setFocusableInTouchMode(false);
            }
        });

        manager = new LinearLayoutManager(BaseApplication.mContext);
        adapter = new SearchAdapter();
        search_recy.setLayoutManager(manager);
        search_recy.setAdapter(adapter);
        adapter.setOnClick(new SearchAdapter.OnClick() {
            @Override
            public void getOnClick(SearchBeans.DataBean.SearchListBean item) {
                if (type == 1) {
                    start(ProjectDetailFragment.newInstance(item.getId(), "", "", 3));
                }
            }
        });
    }

    @Override
    public void getSearchSuccess(SearchBeans searchBeans) {
        if (searchBeans != null && searchBeans.getData() != null) {
            if (searchBeans.getData().getSearchList() != null && searchBeans.getData().getSearchList().size() > 0) {
                adapter.setNewData(searchBeans.getData().getSearchList());
               // ed_search.setFocusable(false);
               // ed_search.setFocusableInTouchMode(false);
            }
        }
    }

    @Override
    public void getSearchFailure(String fail) {
        ToastUtils.showLong(fail);
    }

    /**
     * 搜索
     *
     * @param text
     */
    private void getSearch(String text) {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("search", text);
        p.getSearch(map);
        ed_search.setCursorVisible(false);
        ed_search.setFocusable(false);
        ed_search.setFocusableInTouchMode(false);
        search_cancel.setVisibility(View.GONE);
    }
}
