package com.coahr.thoughtrui.mvp.view.SubjectList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.presenter.FragmentTopicsP;
import com.coahr.thoughtrui.mvp.view.SubjectList.adapter.NodeTreeAdapter;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.BaseNode;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.NodeHelper;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

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

    }

    @Override
    public void initData() {
        getHttp();
        su_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading=true;
                    getHttp();
                }else {
                    su_swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (subjectListBean != null) {
            SubjectListBean.DataBean data = subjectListBean.getData();
            List<SubjectListBean.DataBean.QuestionListBean> questionLists = data.getQuestionList();
            if (questionLists != null && questionLists.size() > 0) {
                baseNodeList.clear();
                for (int i = 0; i < questionLists.size(); i++) {
                    SubjectListBean.DataBean.QuestionListBean questionListBean = questionLists.get(i);
                    //根节点
                    ThreeNode root = new ThreeNode(questionListBean.getId(),"", questionListBean.getName());
                    baseNodeList.add(root);
                    //子节点1
                    if (questionListBean.getQuesList() != null && questionListBean.getQuesList().size() > 0) {
                        for (int j = 0; j < questionListBean.getQuesList().size(); j++) {
//                            Object o = questionListBean.getQuesList().get(j);
//                            ThreeNode rootChild_1 = new ThreeNode(o.getId(), questionListBean.getId(), quesListRoot.getTitle());
//                            baseNodeList.add(rootChild_1);
                        }
                    } else { //子节点2
                        if (questionListBean.getValue()!= null && questionListBean.getValue().size() > 0) {
                            for (int j = 0; j < questionListBean.getValue().size(); j++) {
                                SubjectListBean.DataBean.QuestionListBean.ValueBeanX valueBeanRoot = questionListBean.getValue().get(j);
                                ThreeNode rootChild_2 = new ThreeNode(valueBeanRoot.getId(), questionListBean.getId(), valueBeanRoot.getName());
                                baseNodeList.add(rootChild_2);
                                //孙子节点1
                                if (valueBeanRoot.getQuesList() != null && valueBeanRoot.getQuesList().size() > 0) {
                                    for (int k = 0; k < valueBeanRoot.getQuesList().size(); k++) {
                                        SubjectListBean.DataBean.QuestionListBean.ValueBeanX.QuesListX quesListX = valueBeanRoot.getQuesList().get(k);
                                        //  KLog.d("孙子节点1",quesListChild.getTitle());
                                        ThreeNode root_grand_1 = new ThreeNode(quesListX.getId(), valueBeanRoot.getId(), quesListX.getTitle());
                                        baseNodeList.add(root_grand_1);
                                    }
                                } else {
                                    //孙子节点2
                                    if (valueBeanRoot.getValue() != null && valueBeanRoot.getValue().size() > 0) {
                                        for (int k = 0; k < valueBeanRoot.getValue().size(); k++) {
                                            SubjectListBean.DataBean.QuestionListBean.ValueBeanX.ValueBean valueBean = valueBeanRoot.getValue().get(k);
                                            ThreeNode root_grand_2 = new ThreeNode(valueBean.getId(), valueBeanRoot.getId(), valueBean.getName());
                                            baseNodeList.add(root_grand_2);
                                            //重孙节点 great-grandson
                                            if (valueBean.getQuesList() != null && valueBean.getQuesList().size() > 0) {
                                                for (int l = 0; l < valueBean.getQuesList().size(); l++) {
                                                    SubjectListBean.DataBean.QuestionListBean.ValueBeanX.ValueBean.QuesListBean quesListBean = valueBean.getQuesList().get(l);
                                                    ThreeNode great_grandson = new ThreeNode(quesListBean.getId(), valueBean.getId(), quesListBean.getTitle());
                                                    baseNodeList.add(great_grandson);
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
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
        isLoading=false;
        su_swipe.setRefreshing(false);
    }
    @Override
    public void getSubjectListFailure(String failure,int code) {
        ToastUtils.showLong(failure);
        isLoading=false;
        su_swipe.setRefreshing(false);

        if (code !=-1){

        } else {
            loginDialog();
        }
    }

    private void getHttp() {
        Map map = new HashMap();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("token",Constants.devicestoken);
        map.put("sessionid",Constants.sessionId);
        p.getSubjectList(map);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
        isLoading=false;
        su_swipe.setRefreshing(false);
    }

    /**
     * 登录Dialog
     */
    private void loginDialog(){
        Login_DialogFragment login_dialogFragment=Login_DialogFragment.newInstance(Constants.fragment_myFragment);
        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()){
                    getHttp();
                } else {
                    ToastUtils.showLong("请重新登录");
                }

                //    EventBus.getDefault().postSticky(new Event_Main(1, "登陆成功", page));
            }
        });
        login_dialogFragment.show(getChildFragmentManager(),TAG);
    }
}
