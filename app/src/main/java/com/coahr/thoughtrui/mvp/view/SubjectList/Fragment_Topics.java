package com.coahr.thoughtrui.mvp.view.SubjectList;

import android.view.View;
import android.widget.ListView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.presenter.FragmentTopicsP;
import com.coahr.thoughtrui.mvp.view.SubjectList.adapter.NodeTreeAdapter;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.BaseNode;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.NodeHelper;
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
    private NodeTreeAdapter mAdapter;
    private LinkedList<BaseNode> mLinkedList = new LinkedList<>();
    private List<BaseNode> baseNodeList;
    // @BindView(R.id.subject_recycler)
    // RecyclerView subject_recycler;
    //   private LinearLayoutManager linearLayoutManager;
    //  private List<SubjectListBean.DataBean.QuestionListBean> questionListBeanList=new ArrayList<>();
    //  private ThreeItemAdapter adapter;

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
        subject_tittle.getTvTittle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
        //   linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        //  subject_recycler.setLayoutManager(linearLayoutManager);
        mAdapter = new NodeTreeAdapter(_mActivity, treeList, mLinkedList);
        treeList.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        getHttp();
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
                            Object o = questionListBean.getQuesList().get(j);
                           /* ThreeNode rootChild_1 = new ThreeNode(o.getId(), questionListBean.getId(), quesListRoot.getTitle());
                            baseNodeList.add(rootChild_1);*/
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
                                        Object o = valueBeanRoot.getQuesList().get(k);
                                      //  KLog.d("孙子节点1",quesListChild.getTitle());
                                      //  ThreeNode root_grand_1 = new ThreeNode(quesListChild.getId(), valueBeanRoot.getId(), quesListChild.getTitle());
                                      //  baseNodeList.add(root_grand_1);
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
        // adapter = new ThreeItemAdapter(_mActivity,questionListBeanList);
        // subject_recycler.setAdapter(adapter);
        mLinkedList.addAll(NodeHelper.sortNodes(baseNodes));
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void getSubjectListFailure(String failure) {

    }

    private void getHttp() {
        Map map = new HashMap();
        map.put("projectId", "e274a447c8e44f4396c031d4e933606e");
        p.getSubjectList(map);
    }
}
