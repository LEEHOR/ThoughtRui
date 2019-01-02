package com.coahr.thoughtrui.mvp.view.startProject;

import android.view.View;
import android.widget.ListView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.QuestionList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.ValueBean;
import com.coahr.thoughtrui.mvp.presenter.FragmentTopicsP;
import com.coahr.thoughtrui.mvp.view.SubjectList.NodeTreeAdapter;
import com.coahr.thoughtrui.mvp.view.SubjectList.ThressTest;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.Node;
import com.coahr.thoughtrui.mvp.view.SubjectList.node.NodeHelper;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

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
 */
public class Fragment_Topics extends BaseFragment<FragmentTopicsC.Presenter> implements FragmentTopicsC.View {
    @Inject
    FragmentTopicsP p;
    @BindView(R.id.subject_tittle)
    MyTittleBar subject_tittle;
    @BindView(R.id.id_tree)
    ListView treeList;
    private NodeTreeAdapter mAdapter;
    private LinkedList<Node> mLinkedList = new LinkedList<>();
    private List<Node> nodeList;
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
        nodeList = new ArrayList<>();
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
                nodeList.clear();
                for (int i = 0; i < questionLists.size(); i++) {
                    SubjectListBean.DataBean.QuestionListBean questionListBean = questionLists.get(i);
                    //根节点
                    ThressTest root = new ThressTest(questionListBean.getId(),"", questionListBean.getName());
                    nodeList.add(root);
                    //子节点1
                    if (questionListBean.getQuesList() != null && questionListBean.getQuesList().size() > 0) {
                        for (int j = 0; j < questionListBean.getQuesList().size(); j++) {
                            Object o = questionListBean.getQuesList().get(j);
                           /* ThressTest rootChild_1 = new ThressTest(o.getId(), questionListBean.getId(), quesListRoot.getTitle());
                            nodeList.add(rootChild_1);*/
                        }
                    } else { //子节点2
                        if (questionListBean.getValue()!= null && questionListBean.getValue().size() > 0) {
                            for (int j = 0; j < questionListBean.getValue().size(); j++) {
                                SubjectListBean.DataBean.QuestionListBean.ValueBeanX valueBeanRoot = questionListBean.getValue().get(j);
                                ThressTest rootChild_2 = new ThressTest(valueBeanRoot.getId(), questionListBean.getId(), valueBeanRoot.getName());
                                nodeList.add(rootChild_2);
                                //孙子节点1
                                if (valueBeanRoot.getQuesList() != null && valueBeanRoot.getQuesList().size() > 0) {
                                    for (int k = 0; k < valueBeanRoot.getQuesList().size(); k++) {
                                        Object o = valueBeanRoot.getQuesList().get(k);
                                      //  KLog.d("孙子节点1",quesListChild.getTitle());
                                      //  ThressTest root_grand_1 = new ThressTest(quesListChild.getId(), valueBeanRoot.getId(), quesListChild.getTitle());
                                      //  nodeList.add(root_grand_1);
                                    }
                                } else {
                                    //孙子节点2
                                    if (valueBeanRoot.getValue() != null && valueBeanRoot.getValue().size() > 0) {
                                        for (int k = 0; k < valueBeanRoot.getValue().size(); k++) {
                                            SubjectListBean.DataBean.QuestionListBean.ValueBeanX.ValueBean valueBean = valueBeanRoot.getValue().get(k);
                                            ThressTest root_grand_2 = new ThressTest(valueBean.getId(), valueBeanRoot.getId(), valueBean.getName());
                                            nodeList.add(root_grand_2);
                                            //重孙节点 great-grandson
                                            if (valueBean.getQuesList() != null && valueBean.getQuesList().size() > 0) {
                                                for (int l = 0; l < valueBean.getQuesList().size(); l++) {
                                                    SubjectListBean.DataBean.QuestionListBean.ValueBeanX.ValueBean.QuesListBean quesListBean = valueBean.getQuesList().get(l);
                                                    ThressTest great_grandson = new ThressTest(quesListBean.getId(), valueBean.getId(), quesListBean.getTitle());
                                                    nodeList.add(great_grandson);
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

        setAdapter(nodeList);

    }

    private void setAdapter(List<Node> nodes) {
        // adapter = new ThreeItemAdapter(_mActivity,questionListBeanList);
        // subject_recycler.setAdapter(adapter);
        mLinkedList.addAll(NodeHelper.sortNodes(nodes));
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
