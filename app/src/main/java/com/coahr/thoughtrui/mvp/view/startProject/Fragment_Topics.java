package com.coahr.thoughtrui.mvp.view.startProject;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.FragmentTopicsC;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectChildListAdapterBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectParentListAdapterBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.ValueBean;
import com.coahr.thoughtrui.mvp.presenter.FragmentTopicsP;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:04
 */
public class Fragment_Topics extends BaseFragment<FragmentTopicsC.Presenter> implements FragmentTopicsC.View {
    @Inject
    FragmentTopicsP p;
    private List<SubjectParentListAdapterBean> parentListAdapterBeanList ;
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

    }

    @Override
    public void initData() {
        parentListAdapterBeanList=new ArrayList<>();
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        SubjectListBean.DataBean data = subjectListBean.getData();
        if (data != null) {
            List<SubjectListBean.DataBean.QuestionListBean> questionList = data.getQuestionList();
            if (questionList != null && questionList.size()>0) {
                parentListAdapterBeanList.clear();
                for (int i = 0; i <questionList.size() ; i++) {  //父级
                    SubjectParentListAdapterBean parentListAdapterBean=new SubjectParentListAdapterBean();
                    parentListAdapterBean.setExpand(false);
                    parentListAdapterBean.setId(questionList.get(i).getId());
                    parentListAdapterBean.setName(questionList.get(i).getName());
                    parentListAdapterBean.setType(0);

                    if (questionList.get(i).getQuesList() !=null && questionList.get(i).getQuesList().size()>0){
                        parentListAdapterBean.setQuestList(questionList.get(i).getQuesList());
                    }

                    if (questionList.get(i).getValue()!=null  && questionList.get(i).getValue().size()>0) {//获取子级

                        parentListAdapterBean.setValue(questionList.get(i).getValue());  //加入父级
                                List<SubjectChildListAdapterBean> childListAdapterBeans =new ArrayList<>();
                                childListAdapterBeans.clear();
                                for (int j = 0; j < questionList.get(i).getValue().size(); j++) {  //获取子级
                                    ValueBean valueBean = questionList.get(i).getValue().get(j);
                                    SubjectChildListAdapterBean childListAdapterBean = new SubjectChildListAdapterBean();
                                    childListAdapterBean.setExpand(false);
                                    childListAdapterBean.setId(valueBean.getId());
                                    childListAdapterBean.setName(valueBean.getName());
                                    childListAdapterBean.setType(1);
                                    if (valueBean.getQuesList() != null && valueBean.getQuesList().size() > 0) {
                                        childListAdapterBean.setQuestList(valueBean.getQuesList());
                                    }

                                    if (valueBean.getValueList() != null && valueBean.getValueList().size() > 0) {

                                        childListAdapterBean.setValueBeanList(valueBean.getValueList());   //加入子级
                                        List<SubjectChildListAdapterBean> childListAdapterBeans1 =new ArrayList<>();
                                        childListAdapterBeans1.clear();
                                        for (int k = 0; k < valueBean.getValueList().size(); k++) {
                                            ValueBean valueBean1 = valueBean.getValueList().get(k); //获取孙子级
                                            SubjectChildListAdapterBean childListAdapterBean1 = new SubjectChildListAdapterBean();
                                            childListAdapterBean1.setExpand(false);
                                            childListAdapterBean1.setType(1);
                                            childListAdapterBean1.setId(valueBean1.getId());
                                            childListAdapterBean1.setName(valueBean1.getName());
                                            if (valueBean1.getQuesList() != null && valueBean1.getQuesList().size() > 0) {
                                                childListAdapterBean1.setQuestList(valueBean1.getQuesList());
                                            }
                                            if (valueBean1.getValueList() != null && valueBean1.getValueList().size() > 0) {
                                                    childListAdapterBean1.setValueBeanList(valueBean1.getValueList());
                                            }
                                            childListAdapterBeans1.add(childListAdapterBean1);

                                        }
                                        childListAdapterBean.setSubjectChildListAdapterBean(childListAdapterBeans1);  //孙子级和子级相连
                                    }
                                    childListAdapterBeans.add(childListAdapterBean);

                                }
                                    parentListAdapterBean.setSubjectChildListAdapterBean(childListAdapterBeans); //子父级相连
                            }

                    parentListAdapterBeanList.add(parentListAdapterBean);
                }
            }
        }


    }

    @Override
    public void getSubjectListFailure(String failure) {

    }
}
