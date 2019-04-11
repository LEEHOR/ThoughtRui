package com.coahr.thoughtrui.mvp.view.reviewed;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ReviewMainPagerFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_censor;
import com.coahr.thoughtrui.mvp.view.reviewed.adapter.censor_viewPager_Adapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 审核的Viewpager首页
 */
public class ReviewedFragment extends BaseFragment {
    @BindView(R.id.re_search)
    RelativeLayout re_search;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.search_cancel)
    TextView search_cancel;
    @BindView(R.id.censor_tab)
    TabLayout censor_tab;
    @BindView(R.id.censor_viewpager)
    ViewPager censor_viewpager;
    private censor_viewPager_Adapter adapter;

    @Override
    public ReviewMainPagerFragment_C.Presenter getPresenter() {
        return null;
    }

    public static ReviewedFragment newInstance() {
        return new ReviewedFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_review_list;
    }

    @Override
    public void initView() {
      /*  //手机顶部状态栏颜色适配
        ImmersionBar.with(this)
               .transparentBar()
                .statusBarDarkFont(false)
                .init();*/
        ed_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                search_cancel.setVisibility(View.VISIBLE);
                ed_search.setFocusable(true);
                ed_search.setFocusableInTouchMode(true);
                return false;
            }
        });

        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_search.setFocusable(false);
                ed_search.setFocusableInTouchMode(false);
                search_cancel.setVisibility(View.GONE);
            }
        });

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(v.getText()) && v.getText().toString().equals("")) {
                        ToastUtils.showLong(getResources().getString(R.string.review_fragment_not_null));
                    } else {
                        KeyBoardUtils.hideKeybord(v, _mActivity);
                        EventBus.getDefault().postSticky(new EvenBus_censor(v.getText().toString().trim(), censor_viewpager.getCurrentItem()));
                        return true;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void initData() {
        adapter = new censor_viewPager_Adapter(getChildFragmentManager());
        censor_viewpager.setAdapter(adapter);
        censor_tab.setupWithViewPager(censor_viewpager);
        censor_viewpager.setCurrentItem(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //ImmersionBar.with(this).destroy();
    }
}
