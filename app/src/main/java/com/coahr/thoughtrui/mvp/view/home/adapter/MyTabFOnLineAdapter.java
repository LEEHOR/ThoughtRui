package com.coahr.thoughtrui.mvp.view.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 15:50
 * 首页项目adapter
 */
public class MyTabFOnLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HomeDataList homeDataList;
    private Context context;
    private int type;
    // 0 1 2 新项目 已完成 未完成  为下载
    private int newProject = 0;
    private int complete = 1;
    private int uncomplete = 2;
    private int undownload=3;
    private adapter_online adapter_online;

    public void setHomeDataList(HomeDataList homeDataList,Context context) {
        this.homeDataList = homeDataList;
        this.context=context;
        getItemCount();
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == newProject) {
            return new newListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_new, viewGroup, false));
        } else if (i == complete){
            return new completeListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_complete, viewGroup, false));
        } else if (i == uncomplete){
            return  new unCompleteListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_uncomplete, viewGroup, false));
        } else if (i==undownload){
            return new unDownLoadListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_undownload, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder!=null&&viewHolder.itemView!=null) {
        if (viewHolder instanceof newListHaveBeenCancelViewHolder){
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_schedule.setText(homeDataList.getData().getNewList().get(i).getProgress());
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_explain.setText(homeDataList.getData().getNewList().get(i).getInspect()==1?"飞检"
                    :homeDataList.getData().getNewList().get(i).getInspect()==2?"神秘顾客"
                     :homeDataList.getData().getNewList().get(i).getInspect()==3?"新店验收":"飞检");
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_times.setText("["+TimeUtils.getStringDate_start(homeDataList.getData().getNewList().get(i).getStartTime())+","+TimeUtils.getStringDate_end(homeDataList.getData().getNewList().get(i).getEndTime())+"]");
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_code.setText(homeDataList.getData().getNewList().get(i).getCode());
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_name.setText(homeDataList.getData().getNewList().get(i).getPname());
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_company.setText(homeDataList.getData().getNewList().get(i).getDname());
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_address.setText(homeDataList.getData().getNewList().get(i).getAreaAddress());
            ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_update_time.setText(TimeUtils.getStringDate_start(homeDataList.getData().getNewList().get(i).getModifyTime()));
            ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapter_online != null) {
                        adapter_online.newListClick(homeDataList.getData().getNewList().get(i));
                    }
                }
            });
            ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (adapter_online != null) {
                        adapter_online.newListLongClick(homeDataList.getData().getNewList().get(i));
                    }
                    return false;
                }
            });
        }
            if (viewHolder instanceof completeListHaveBeenCancelViewHolder){
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_schedule.setText(homeDataList.getData().getCompleteList().get(i).getProgress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_explain.setText(homeDataList.getData().getCompleteList().get(i).getInspect()==1?"飞检"
                        :homeDataList.getData().getCompleteList().get(i).getInspect()==2?"神秘顾客"
                        :homeDataList.getData().getCompleteList().get(i).getInspect()==3?"新店验收":"飞检");
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_times.setText("["+TimeUtils.getStringDate_start(homeDataList.getData().getCompleteList().get(i).getStartTime())+","+TimeUtils.getStringDate_end(homeDataList.getData().getCompleteList().get(i).getEndTime())+"]");
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_code.setText(homeDataList.getData().getCompleteList().get(i).getCode());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_name.setText(homeDataList.getData().getCompleteList().get(i).getPname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_company.setText(homeDataList.getData().getCompleteList().get(i).getDname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_address.setText(homeDataList.getData().getCompleteList().get(i).getAreaAddress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_update_time.setText(TimeUtils.getStringDate_start(homeDataList.getData().getCompleteList().get(i).getModifyTime()));
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.completeClick(homeDataList.getData().getCompleteList().get(i));
                        }
                    }
                });
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.completeLongClick(homeDataList.getData().getCompleteList().get(i));
                        }
                        return false;
                    }
                });
            }

            if (viewHolder instanceof unCompleteListHaveBeenCancelViewHolder){
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_schedule.setText(homeDataList.getData().getUnCompleteList().get(i).getProgress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_explain.setText(homeDataList.getData().getUnCompleteList().get(i).getInspect()==1?"飞检"
                        :homeDataList.getData().getUnCompleteList().get(i).getInspect()==2?"神秘顾客"
                        :homeDataList.getData().getUnCompleteList().get(i).getInspect()==3?"新店验收":"飞检");
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_times.setText("["+TimeUtils.getStringDate_start(homeDataList.getData().getUnCompleteList().get(i).getStartTime())+","
                                                                                                                 +TimeUtils.getStringDate_end(homeDataList.getData().getUnCompleteList().get(i).getEndTime())+"]");
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_code.setText(homeDataList.getData().getUnCompleteList().get(i).getCode());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_name.setText(homeDataList.getData().getUnCompleteList().get(i).getPname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_company.setText(homeDataList.getData().getUnCompleteList().get(i).getDname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_address.setText(homeDataList.getData().getUnCompleteList().get(i).getAreaAddress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_update_time.setText(TimeUtils.getStringDate_start(homeDataList.getData().getUnCompleteList().get(i).getModifyTime()));
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unCompleteClick(homeDataList.getData().getUnCompleteList().get(i));
                        }
                    }
                });
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unCompleteLongClick(homeDataList.getData().getUnCompleteList().get(i));
                        }
                        return false;
                    }
                });
            }

            if (viewHolder instanceof unDownLoadListHaveBeenCancelViewHolder){
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_schedule.setText(homeDataList.getData().getNewList().get(i).getProgress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_explain.setText(homeDataList.getData().getNewList().get(i).getInspect()==1?"飞检"
                        :homeDataList.getData().getNewList().get(i).getInspect()==2?"神秘顾客"
                        :homeDataList.getData().getNewList().get(i).getInspect()==3?"新店验收":"飞检");
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_times.setText("["+TimeUtils.getStringDate_start(homeDataList.getData().getNewList().get(i).getStartTime())+","
                        +TimeUtils.getStringDate_end(homeDataList.getData().getNewList().get(i).getEndTime())+"]");
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_code.setText(homeDataList.getData().getNewList().get(i).getCode());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_name.setText(homeDataList.getData().getNewList().get(i).getPname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_company.setText(homeDataList.getData().getNewList().get(i).getDname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_address.setText(homeDataList.getData().getNewList().get(i).getAreaAddress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unDownLoadClick(homeDataList.getData().getNewList().get(i));
                        }
                    }
                });
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unDownLoadLongClick(homeDataList.getData().getNewList().get(i));
                        }
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (homeDataList != null) {
            if (type == 0) {
                return homeDataList.getData().getNewList().size();
            } else if (type == 1) {
                return homeDataList.getData().getCompleteList().size();
            } else if (type == 2) {
                return homeDataList.getData().getUnCompleteList().size();
            } else if (type == 3) {
                return homeDataList.getData().getAllList().size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 0) {  //新项目
           if( homeDataList.getData().getNewList().get(position).getCompleteStatus() ==1 && homeDataList.getData().getNewList().get(position).getDownloadTime() != -1){
                return newProject;
            } else if (homeDataList.getData().getNewList().get(position).getCompleteStatus() ==1 && homeDataList.getData().getNewList().get(position).getDownloadTime() == -1){
               return undownload;
           } else {
               return newProject;
           }
        } else if (type == 1) {  //已完成
            return complete;
        } else if (type == 2) { //未完成
            return uncomplete;
        } else if (type == 3) { //全部
            if (homeDataList.getData().getAllList().get(position).getCompleteStatus() == 1 && homeDataList.getData().getAllList().get(position).getDownloadTime() != -1) {//新项目
                return newProject;
            } else if (homeDataList.getData().getAllList().get(position).getCompleteStatus() == 2) {  //未完成
                return uncomplete;
            } else if (homeDataList.getData().getAllList().get(position).getCompleteStatus() == 3) {  //完成
                return complete;
            } else if (homeDataList.getData().getAllList().get(position).getCompleteStatus() == 1 && homeDataList.getData().getAllList().get(position).getDownloadTime() == -1) { //未下载
                return undownload;
            } else {
                return newProject;
            }
        } else {
            return uncomplete;
        }
    }

    public class newListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView new_cardView;
        private  TextView new_tv_schedule;
        private  TextView new_tv_explain;
        private  TextView new_tv_project_times;
        private  TextView new_tv_project_code;
        private  TextView new_tv_project_name;
        private  TextView new_tv_project_company;
        private  TextView new_tv_project_address;
        private  TextView new_tv_update_time;
        public newListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            new_cardView=itemView.findViewById(R.id.new_cardView);
            new_tv_schedule = itemView.findViewById(R.id.new_tv_schedule);
            new_tv_explain= itemView.findViewById(R.id.new_tv_explain);
            new_tv_project_times=  itemView.findViewById(R.id.new_tv_project_times);
            new_tv_project_code= itemView.findViewById(R.id.new_tv_project_code);
            new_tv_project_name= itemView.findViewById(R.id.new_tv_project_name);
            new_tv_project_company= itemView.findViewById(R.id.new_tv_project_company);
            new_tv_project_address=itemView.findViewById(R.id.new_tv_project_address);
            new_tv_update_time=itemView.findViewById(R.id.new_tv_update_time);

        }
    }

    public class completeListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView complete_cardView;
        private  TextView complete_tv_schedule;
        private  TextView complete_tv_explain;
        private  TextView complete_tv_project_times;
        private  TextView complete_tv_project_code;
        private  TextView complete_tv_project_name;
        private  TextView complete_tv_project_company;
        private  TextView complete_tv_project_address;
        private  TextView complete_tv_update_time;
        public completeListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            complete_cardView=itemView.findViewById(R.id.complete_cardView);
            complete_tv_schedule = itemView.findViewById(R.id.complete_tv_schedule);
            complete_tv_explain= itemView.findViewById(R.id.complete_tv_explain);
            complete_tv_project_times=  itemView.findViewById(R.id.complete_tv_project_times);
            complete_tv_project_code= itemView.findViewById(R.id.complete_tv_project_code);
            complete_tv_project_name= itemView.findViewById(R.id.complete_tv_project_name);
            complete_tv_project_company= itemView.findViewById(R.id.complete_tv_project_company);
            complete_tv_project_address=itemView.findViewById(R.id.complete_tv_project_address);
            complete_tv_update_time=itemView.findViewById(R.id.complete_tv_update_time);

        }
    }

    public class unCompleteListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView uncomplete_cardView;
        private  TextView unComplete_tv_schedule;
        private  TextView unComplete_tv_explain;
        private  TextView unComplete_tv_project_times;
        private  TextView unComplete_tv_project_code;
        private  TextView unComplete_tv_project_name;
        private  TextView unComplete_tv_project_company;
        private  TextView unComplete_tv_project_address;
        private  TextView unComplete_tv_update_time;
        private  TextView unComplete_item_data;

        public unCompleteListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            uncomplete_cardView=itemView.findViewById(R.id.uncomplete_cardView);
            unComplete_tv_schedule = itemView.findViewById(R.id.unComplete_tv_schedule);
            unComplete_tv_explain= itemView.findViewById(R.id.unComplete_tv_explain);
            unComplete_tv_project_times=  itemView.findViewById(R.id.unComplete_tv_project_times);
            unComplete_tv_project_code= itemView.findViewById(R.id.unComplete_tv_project_code);
            unComplete_tv_project_name= itemView.findViewById(R.id.unComplete_tv_project_name);
            unComplete_tv_project_company= itemView.findViewById(R.id.unComplete_tv_project_company);
            unComplete_tv_project_address=itemView.findViewById(R.id.unComplete_tv_project_address);
            unComplete_tv_update_time=itemView.findViewById(R.id.unComplete_tv_update_time);
            unComplete_item_data = itemView.findViewById(R.id.unComplete_item_data);
        }
    }

    public class unDownLoadListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView undownload_cardView;
        private  TextView undownload_tv_schedule;
        private  TextView undownload_tv_explain;
        private  TextView undownload_tv_project_times;
        private  TextView undownload_tv_project_code;
        private  TextView undownload_tv_project_name;
        private  TextView undownload_tv_project_company;
        private  TextView undownload_tv_project_address;
        public unDownLoadListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            undownload_cardView=itemView.findViewById(R.id.undownload_cardView);
            undownload_tv_schedule = itemView.findViewById(R.id.undownload_tv_schedule);
            undownload_tv_explain= itemView.findViewById(R.id.undownload_tv_explain);
            undownload_tv_project_times=  itemView.findViewById(R.id.undownload_tv_project_times);
            undownload_tv_project_code= itemView.findViewById(R.id.undownload_tv_project_code);
            undownload_tv_project_name= itemView.findViewById(R.id.undownload_tv_project_name);
            undownload_tv_project_company= itemView.findViewById(R.id.undownload_tv_project_company);
            undownload_tv_project_address=itemView.findViewById(R.id.undownload_tv_project_address);
        }
    }

    public void setAdapter_online(MyTabFOnLineAdapter.adapter_online adapter_online) {
        this.adapter_online = adapter_online;
    }

    public  interface adapter_online{
        void newListClick(HomeDataList.DataBean.newListBean newListBean);
        void newListLongClick(HomeDataList.DataBean.newListBean newListBean);

        void completeClick(HomeDataList.DataBean.CompleteListBean completeListBean);
        void completeLongClick(HomeDataList.DataBean.CompleteListBean completeListBean);

        void unCompleteClick(HomeDataList.DataBean.UnCompleteListBean unCompleteListBean);
        void unCompleteLongClick(HomeDataList.DataBean.UnCompleteListBean unCompleteListBean);

        void unDownLoadClick(HomeDataList.DataBean.newListBean newListBean);
        void unDownLoadLongClick(HomeDataList.DataBean.newListBean newListBean);
    }
}
