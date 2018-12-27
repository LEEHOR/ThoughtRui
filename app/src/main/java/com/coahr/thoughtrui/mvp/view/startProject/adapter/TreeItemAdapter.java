package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectChildListAdapterBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectParentListAdapterBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 16:49
 */
public class TreeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SubjectParentListAdapterBean> parentListAdapterBeanList;
    private Context context;
    private LayoutInflater mInflater;
    private OnScrollListener mOnScrollListener;

    public TreeItemAdapter(Context context, List<SubjectParentListAdapterBean> subjectParentListAdapterBeanList) {
        this.context = context;
        this.parentListAdapterBeanList = subjectParentListAdapterBeanList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case SubjectParentListAdapterBean.PARENT_ITEM:
                view = mInflater.inflate(R.layout.item_recycler_parent_threeadapter, viewGroup, false);
                return new ParentViewHolder(view);
            case SubjectParentListAdapterBean.CHILD_ITEM:
                view = mInflater.inflate(R.layout.item_recycler_child_threeadapter, viewGroup, false);
                return new ChildViewHolder(view);
            default:
                view = mInflater.inflate(R.layout.item_recycler_parent_threeadapter, viewGroup, false);
                return new ParentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case SubjectParentListAdapterBean.PARENT_ITEM:
                ParentViewHolder parentViewHolder = (ParentViewHolder) viewHolder;
                parentViewHolder.bindView(parentListAdapterBeanList.get(i), i, itemClickListener);
                break;
            case SubjectParentListAdapterBean.CHILD_ITEM:
                ChildViewHolder childViewHolder = (ChildViewHolder) viewHolder;
                childViewHolder.bindView(parentListAdapterBeanList.get(i), i);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return parentListAdapterBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return parentListAdapterBeanList.get(position).getType();
    }

    /**
     * 在父布局下方插入一条数据
     *
     * @param bean
     * @param position
     */
    public void add(List<SubjectParentListAdapterBean> bean, int position) {
        if (bean != null) {
            for (int i = 0; i <bean.size() ; i++) {
                parentListAdapterBeanList.add(position+i, bean.get(i));
                notifyItemInserted(position+i);
            }
        }


    }

    /**
     * 移除子布局数据
     *
     * @param position
     */
    protected void remove(int position,int size) {
        for (int i = 0; i <size ; i++) {
            parentListAdapterBeanList.remove(position+i);
            notifyItemRemoved(position+i);
        }

    }

    /**
     * 确定当前点击的item位置并返回
     *
     * @param uuid
     * @return
     */
    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < parentListAdapterBeanList.size(); i++) {
            if (uuid.equalsIgnoreCase(parentListAdapterBeanList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 封装子布局数据对象并返回
     * 注意，此处只是重新封装一个DataBean对象，为了标注Type为子布局数据，进而展开，展示数据
     * 要和onHideChildren方法里的getChildBean()区分开来
     *把子类转换为父类
     * @param bean
     * @return
     */
    private List<SubjectParentListAdapterBean> getChildDataBean(SubjectParentListAdapterBean bean) {
         List<SubjectParentListAdapterBean> list=new ArrayList<>();
        list.clear();
        List<SubjectChildListAdapterBean> subjectChildListAdapterBean = bean.getSubjectChildListAdapterBean();
        if (subjectChildListAdapterBean != null && subjectChildListAdapterBean.size()>0) {
            for (int i = 0; i < subjectChildListAdapterBean.size(); i++) {
                SubjectParentListAdapterBean parentListAdapterBean=new SubjectParentListAdapterBean();
                parentListAdapterBean.setId(subjectChildListAdapterBean.get(i).getId());
                parentListAdapterBean.setName(subjectChildListAdapterBean.get(i).getName());
                parentListAdapterBean.setExpand(false);
                if (subjectChildListAdapterBean.get(i).getQuestList() !=null && subjectChildListAdapterBean.get(i).getQuestList().size()>0 ){
                    parentListAdapterBean.setType(1);
                    parentListAdapterBean.setQuestList(subjectChildListAdapterBean.get(i).getQuestList());
                }
                if (subjectChildListAdapterBean.get(i).getValueBeanList() !=null && subjectChildListAdapterBean.get(i).getValueBeanList().size()>0){
                    parentListAdapterBean.setType(0);
                    parentListAdapterBean.setValue(subjectChildListAdapterBean.get(i).getValueBeanList());
                }
                list.add(parentListAdapterBean);
            }

        }
        return list;
    }

    private ExpandListClickListener itemClickListener = new ExpandListClickListener() {

        @Override
        public void onExpandChildren(SubjectParentListAdapterBean entity) {
            int position = getCurrentPosition(entity.getId());//确定当前点击的item位置
            List<SubjectParentListAdapterBean> childDataBean = getChildDataBean(entity);
            if (childDataBean == null) {
                return;
            }
            add(childDataBean, position + 1);//在当前的item下方插入
            System.out.println(position + "------------");
            if (position == parentListAdapterBeanList.size() - 2 && mOnScrollListener != null) { //如果点击的item为最后一个
                mOnScrollListener.scrollTo(position + 1);//向下滚动，使子布局能够完全展示
            }

        }

        @Override
        public void onHideChildren(SubjectParentListAdapterBean entity) {
            int position = getCurrentPosition(entity.getId());//确定当前点击的item位置
            List<SubjectChildListAdapterBean> subjectChildListAdapterBean = entity.getSubjectChildListAdapterBean();//获取子布局对象
            if (subjectChildListAdapterBean == null ) {
                return;
            }
            remove(position + 1,subjectChildListAdapterBean.size());//删除
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position);
            }

        }
    };

    /**
     * 滚动监听接口
     */
    public interface OnScrollListener {
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    //======================================ViewHolder======================================
    //父级holder
    public class ParentViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tv_title;
        private LinearLayout linearLayout;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void bindView(final SubjectParentListAdapterBean dataBean, final int pos, final ExpandListClickListener listener) {
            linearLayout = view.findViewById(R.id.parent_line);
            tv_title = view.findViewById(R.id.tv_parent_title);
            tv_title.setText(dataBean.getName());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        if (dataBean.isExpand()) {
                            listener.onHideChildren(dataBean);
                            dataBean.setExpand(false);

                        } else {
                            listener.onExpandChildren(dataBean);
                            dataBean.setExpand(true);
                        }
                    }
                }
            });
        }
    }

    //子级holder
    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private RelativeLayout child_re;
        private TextView tv_child_title;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void bindView(final SubjectParentListAdapterBean dataBean, final int pos) {
            child_re = view.findViewById(R.id.child_re);
            tv_child_title = view.findViewById(R.id.tv_child_title);
            tv_child_title.setText(dataBean.getName());
            child_re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KLog.d("name", dataBean.getName());
                }
            });
        }
    }

}
