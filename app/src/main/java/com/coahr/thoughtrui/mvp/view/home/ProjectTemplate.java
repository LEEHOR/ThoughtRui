package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;
import com.coahr.thoughtrui.mvp.presenter.ProjectTemplate_P;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.home.adapter.ProjectTemplateAdapter;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：项目模板
 */
public class ProjectTemplate extends BaseFragment<ProjectTemplate_c.Presenter> implements ProjectTemplate_c.View {

    @Inject
    ProjectTemplate_P p;
    @BindView(R.id.template_title)
    MyTittleBar template_title;
    @BindView(R.id.template_recycle)
    RecyclerView template_recycle;
    @BindView(R.id.template_swipe)
    SwipeRefreshLayout template_swipe;
    private ProjectTemplateAdapter templateAdapter;
    private boolean isLoad;

    public static ProjectTemplate newInstance() {
        return new ProjectTemplate();
    }

    @Override
    public ProjectTemplate_c.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_projecttemplate;
    }

    @Override
    public void initView() {
        //手机顶部状态栏颜色适配
        ImmersionBar.with(this)
                .statusBarColor(R.color.material_blue_350)
                .statusBarDarkFont(false)
                .init();
        template_title.getRoot().setBackgroundColor(Color.rgb(58, 128, 255));
        template_title.getTvTittle().setTextColor(Color.rgb(255, 255, 255));
        template_title.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        if (haslogin()) {
            if (Constants.isNetWorkConnect) {
                getList();
            } else {
                ToastUtils.showLong(getResources().getString(R.string.toast_3));
            }
        } else {
            loginDialog();
        }

        LinearLayoutManager manager = new LinearLayoutManager(BaseApplication.mContext);
        templateAdapter = new ProjectTemplateAdapter();
        template_recycle.setLayoutManager(manager);
        template_recycle.setAdapter(templateAdapter);
        template_recycle.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 0), DensityUtils.dp2px(BaseApplication.mContext, 5)));
        for (int i = 0; i < template_recycle.getItemDecorationCount(); i++) {
            if (i != 0) {
                template_recycle.removeItemDecorationAt(i);
            }
        }

        template_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoad) {

                    if (haslogin()) {
                        if (Constants.isNetWorkConnect) {
                            isLoad = true;
                            getList();
                        } else {
                            template_swipe.setRefreshing(false);
                            ToastUtils.showLong(getResources().getString(R.string.toast_3));
                        }
                    } else {
                        isLoad = false;
                        template_swipe.setRefreshing(false);
                        loginDialog();
                    }
                } else {
                    template_swipe.setRefreshing(false);
                }
            }
        });

        templateAdapter.setOnClick(new ProjectTemplateAdapter.OnClick() {
            @Override
            public void getOnClick(Template_list.TemplateListBean item) {
                JumpToProjectTemplate(item.getId());
            }
        });
    }

    /**
     * 跳转到经销商
     */
    private void JumpToProjectTemplate(String Template_id){
        start(DealerFragment.Instance(Template_id));
      /*  Intent intent=new Intent(_mActivity, ConstantsActivity.class);
        intent.putExtra("from", Constants.fragment_mainInfo);
         intent.putExtra("Template_id",Template_id);
        intent.putExtra("to", Constants.fragment_Dealer);
        startActivity(intent);*/
    }
    @Override
    public void getProjectTemplatesSuccess(Template_list template_list) {
        isLoad = false;
        if (template_list != null) {
            templateAdapter.setNewData(template_list.getTemplate_list());
        }
        template_swipe.setRefreshing(false);
    }

    @Override
    public void getProjectTemplateFailure(String fail) {
        ToastUtils.showLong(getResources().getString(R.string.toast_4));
        isLoad = false;
        template_swipe.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    /**
     * 联网获取
     */
    private void getList() {
        Map map = new HashMap();
        map.put("userId", Constants.sessionId);
        p.getProjectTemplates(map);
    }

    /**
     * 网络类型提示
     *
     * @param title
     * @param Content
     * @param types
     */
    private void NetWorkDialog(String title, String Content, final int types) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (types == 1 || types == 2) {

                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.MyTabFragmentCode);

        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()) {
                    if (Constants.isNetWorkConnect) {
                        getList();
                    }
                }
            }
        });
        login_dialogFragment.show(getFragmentManager(), TAG);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        isLoad = false;
        template_swipe.setRefreshing(false);
        ToastUtils.showLong(e.toString());

    }
}
