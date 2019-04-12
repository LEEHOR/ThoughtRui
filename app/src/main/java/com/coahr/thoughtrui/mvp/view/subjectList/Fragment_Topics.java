package com.coahr.thoughtrui.mvp.view.subjectList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.usage.UsageEvents;
import android.view.View;
import android.widget.ListView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_SubjectList_id;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.presenter.FragmentTopicsP;
import com.coahr.thoughtrui.mvp.view.subjectList.adapter.NodeTreeAdapter;
import com.coahr.thoughtrui.mvp.view.subjectList.node.BaseNode;
import com.coahr.thoughtrui.mvp.view.subjectList.node.NodeHelper;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:04
 * 题目列表
 */
public class Fragment_Topics extends BaseFragment<FragmentTopicsC.Presenter> implements FragmentTopicsC.View {
    @Inject
    FragmentTopicsP p;
    @BindView(R.id.subject_tittle)
    MyTittleBar subject_tittle;
    @BindView(R.id.id_tree)
    ListView treeList;
    @BindView(R.id.su_swipe)
    SwipeRefreshLayout su_swipe;
    private boolean isLoading;
    private NodeTreeAdapter mAdapter;
    private LinkedList<BaseNode> mLinkedList = new LinkedList<>();
    private List<BaseNode> baseNodeList;

    public static Fragment_Topics newInstance() {
        Fragment_Topics fragmentTopics = new Fragment_Topics();
        return fragmentTopics;
    }

    @Override
    public FragmentTopicsC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_subjectlist;
    }

    @Override
    public void initView() {
        baseNodeList = new ArrayList<>();
        subject_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });

        mAdapter = new NodeTreeAdapter(_mActivity, treeList, mLinkedList);
        treeList.setAdapter(mAdapter);
        mAdapter.setOnThreeClick(new NodeTreeAdapter.onThreeClick() {
            @Override
            public void threeHt_idOnClick(String ht_id) {
                EventBus.getDefault().postSticky(new EvenBus_SubjectList_id(ht_id));
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        getHttp();
        su_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    getHttp();
                } else {
                    su_swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (subjectListBean != null) {
            SubjectListBean.DataBean data = subjectListBean.getData();
            List<SubjectListBean.DataBean.QuestionListRoot> questionListRootList = data.getQuestionList();
            if (questionListRootList != null && questionListRootList.size() > 0) {
                baseNodeList.clear();
                for (int i = 0; i < questionListRootList.size(); i++) {
                    SubjectListBean.DataBean.QuestionListRoot questionListRoot = questionListRootList.get(i);
                    //根节点
                    ThreeNode root = new ThreeNode(questionListRoot.getId(), "", questionListRoot.getName());
                    baseNodeList.add(root);
                    //子节点1
                    if (questionListRoot.getQuesList_1() != null && questionListRoot.getQuesList_1().size() > 0) {
                        for (int j = 0; j < questionListRoot.getQuesList_1().size(); j++) {

                            SubjectListBean.DataBean.QuestionListRoot.QuesListBean_1 quesListBean_1 = questionListRoot.getQuesList_1().get(j);
                            ThreeNode rootChild_1 = new ThreeNode(quesListBean_1.getId(), questionListRoot.getId(), quesListBean_1.getTitle());
                            baseNodeList.add(rootChild_1);
                        }
                    } else {  //子节点2

                    }
                }
            }
        }
        setAdapter(baseNodeList);

    }

    private void setAdapter(List<BaseNode> baseNodes) {
        mLinkedList.clear();
        mLinkedList.addAll(NodeHelper.sortNodes(baseNodes));
        mAdapter.setLinkList(mLinkedList);
        mAdapter.notifyDataSetChanged();
        isLoading = false;
        su_swipe.setRefreshing(false);
    }

    @Override
    public void getSubjectListFailure(String failure, int code) {
        ToastUtils.showLong(failure);
        isLoading = false;
        su_swipe.setRefreshing(false);

        if (code != -1) {

        } else {
            loginDialog();
        }
    }

    private void getHttp() {
        Map map = new HashMap();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("token", Constants.devicestoken);
        map.put("sessionid", Constants.sessionId);
        p.getSubjectList(map);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
        isLoading = false;
        su_swipe.setRefreshing(false);
    }

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.fragment_myFragment);
        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()) {
                    getHttp();
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_10));
                }

                //    EventBus.getDefault().postSticky(new Event_Main(1, "登陆成功", page));
            }
        });
        login_dialogFragment.show(getChildFragmentManager(), TAG);
    }
}
