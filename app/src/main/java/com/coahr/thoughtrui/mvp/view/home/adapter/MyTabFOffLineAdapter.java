package com.coahr.thoughtrui.mvp.view.home.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.ReviewScoreUtil;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 15:50
 * 首页项目adapter
 */
public class MyTabFOffLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProjectsDB> projectsDBList;
    private Context context;
    private int type;
    // 0 1 2 新项目 已完成 未完成  为下载
    private int newProject = 0;
    private int complete = 1;
    private int uncomplete = 2;
    private int undownload = 3;
    private adapter_offline adapter_offline;

    public void setAdapter_offline(adapter_offline adapter_offline) {
        this.adapter_offline = adapter_offline;
    }

    public void setHomeDataList(List<ProjectsDB> projectsDBList, Context context) {
        this.projectsDBList = projectsDBList;
        this.context = context;
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
        } else if (i == complete) {
            return new completeListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_complete, viewGroup, false));
        } else if (i == uncomplete) {
            return new unCompleteListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_uncomplete, viewGroup, false));
        } else if (i == undownload) {

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder != null && viewHolder.itemView != null) {
            if (viewHolder instanceof newListHaveBeenCancelViewHolder) {
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_schedule.setText(projectsDBList.get(i).getSale_code() !=null?projectsDBList.get(i).getSale_code():projectsDBList.get(i).getService_code());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_times.setText(projectsDBList.get(i).getPname());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_code.setText(projectsDBList.get(i).getCode());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_name.setText(projectsDBList.get(i).getPname());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_company.setText(projectsDBList.get(i).getDname());
               // ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_address.setText(projectsDBList.get(i).getAddress());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_update_time.setText(TimeUtils.getStringDate_start(projectsDBList.get(i).getModifyTime()));
                ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.newListClick(projectsDBList.get(i));
                        }
                    }
                });
                ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.newListLongClick(projectsDBList.get(i));
                        }
                        return false;
                    }
                });
            }
            if (viewHolder instanceof completeListHaveBeenCancelViewHolder) {
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_schedule.setText(projectsDBList.get(i).getProgress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_explain.setText(projectsDBList.get(i).getSale_code() !=null?projectsDBList.get(i).getSale_code():projectsDBList.get(i).getService_code());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_times.setText(projectsDBList.get(i).getPname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_code.setText(projectsDBList.get(i).getCode());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_name.setText(projectsDBList.get(i).getPname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_company.setText(projectsDBList.get(i).getDname());
                //((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_address.setText(projectsDBList.get(i).getAddress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_update_time.setText(TimeUtils.getStringDate_start(projectsDBList.get(i).getModifyTime()));

                List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectsDBList.get(i).getPid());
                int totalScore = 0;
                if (projectsDBS != null && projectsDBS.size() > 0) {
                    List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
                    totalScore = ReviewScoreUtil.getTotalScore(subjectsDBS);
                }
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_score.setText("检核得分: " + totalScore + "分");


                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.completeClick(projectsDBList.get(i));
                        }
                    }
                });
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.completeLongClick(projectsDBList.get(i), i);
                        }
                        return false;
                    }
                });
            }

            if (viewHolder instanceof unCompleteListHaveBeenCancelViewHolder) {
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_schedule.setText(projectsDBList.get(i).getProgress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_explain.setText(projectsDBList.get(i).getSale_code() !=null?projectsDBList.get(i).getSale_code():projectsDBList.get(i).getService_code());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_times.setText(projectsDBList.get(i).getPname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_code.setText(projectsDBList.get(i).getCode());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_name.setText(projectsDBList.get(i).getPname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_company.setText(projectsDBList.get(i).getDname());
                //((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_address.setText(projectsDBList.get(i).getAddress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_update_time.setText(TimeUtils.getStringDate_start(projectsDBList.get(i).getModifyTime()));
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_item_data.setText(getItemDate(projectsDBList.get(i).getPid()));

                List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectsDBList.get(i).getPid());
                int totalScore = 0;
                if (projectsDBS != null && projectsDBS.size() > 0) {
                    List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
                    totalScore = ReviewScoreUtil.getTotalScore(subjectsDBS);
                }
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_score.setText("检核得分: " + totalScore + "分");

                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.unCompleteClick(projectsDBList.get(i));
                        }
                    }
                });
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.unCompleteLongClick(projectsDBList.get(i), i);
                        }
                        return false;
                    }
                });
            }

            if (viewHolder instanceof unDownLoadListHaveBeenCancelViewHolder) {
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_schedule.setText(projectsDBList.get(i).getProgress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_explain.setText(projectsDBList.get(i).getSale_code() !=null?projectsDBList.get(i).getSale_code():projectsDBList.get(i).getService_code());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_times.setText(projectsDBList.get(i).getPname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_code.setText(projectsDBList.get(i).getCode());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_name.setText(projectsDBList.get(i).getPname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_company.setText(projectsDBList.get(i).getDname());
                //((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_address.setText(projectsDBList.get(i).getAddress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.unDownLoadClick(projectsDBList.get(i));
                        }
                    }
                });
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_offline != null) {
                            adapter_offline.unDownLoadLongClick(projectsDBList.get(i));
                        }
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (projectsDBList != null && projectsDBList.size() > 0) {

            return projectsDBList.size();

        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
     /*   if (type == 0) {  //新项目
         *//*  if( projectsDBList.get(position).getCompleteStatus() ==1 && projectsDBList.get(position).getDownloadTime() != -1){
                return newProject;
            } else if (projectsDBList.get(position).getCompleteStatus() ==1 && projectsDBList.get(position).getDownloadTime() == -1){
               return undownload;
           } else {
               return newProject;
           }*//*
            return uncomplete;
        } */
        if (type == 0) {  //全部
            if (projectsDBList.get(position).getCompleteStatus() == 2 || projectsDBList.get(position).getCompleteStatus() == 1) {  //未完成
                return uncomplete;
            }
            if (projectsDBList.get(position).getCompleteStatus() == 3) {  //完成
                return complete;
            }

        }
        if (type == 1) { //未完成
            return uncomplete;
        }

        if (type == 2) { //已完成
            return complete;
           /* if (projectsDBList.get(position).getCompleteStatus() == 1 && projectsDBList.get(position).getDownloadTime() != -1) {//新项目
                return newProject;
            }*/

         /*   if (projectsDBList.get(position).getCompleteStatus() == 1 && projectsDBList.get(position).getDownloadTime() == -1) { //未下载
                return undownload;
            } else {
                return newProject;
            }*/

        }
        return complete;
    }

    public class newListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView new_cardView;
        private TextView new_tv_schedule;
        private TextView new_tv_explain;
        private TextView new_tv_project_times;
        private TextView new_tv_project_code;
        private TextView new_tv_project_name;
        private TextView new_tv_project_company;
        private TextView new_tv_project_address;
        private TextView new_tv_update_time;

        public newListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            new_cardView = itemView.findViewById(R.id.new_cardView);
            new_tv_schedule = itemView.findViewById(R.id.new_tv_schedule);
            new_tv_explain = itemView.findViewById(R.id.new_tv_explain);
            new_tv_project_times = itemView.findViewById(R.id.new_tv_project_times);
            new_tv_project_code = itemView.findViewById(R.id.new_tv_project_code);
            new_tv_project_name = itemView.findViewById(R.id.new_tv_project_name);
            new_tv_project_company = itemView.findViewById(R.id.new_tv_project_company);
            new_tv_project_address = itemView.findViewById(R.id.new_tv_project_address);
            new_tv_update_time = itemView.findViewById(R.id.new_tv_update_time);

        }
    }

    public class completeListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView complete_cardView;
        private TextView complete_tv_schedule;
        private TextView complete_tv_explain;
        private TextView complete_tv_project_times;
        private TextView complete_tv_project_code;
        private TextView complete_tv_project_name;
        private TextView complete_tv_project_company;
        private TextView complete_tv_project_score;
        private TextView complete_tv_project_address;
        private TextView complete_tv_update_time;

        public completeListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            complete_cardView = itemView.findViewById(R.id.complete_cardView);
            complete_tv_schedule = itemView.findViewById(R.id.complete_tv_schedule);
            complete_tv_explain = itemView.findViewById(R.id.complete_tv_explain);
            complete_tv_project_times = itemView.findViewById(R.id.complete_tv_project_times);
            complete_tv_project_code = itemView.findViewById(R.id.complete_tv_project_code);
            complete_tv_project_name = itemView.findViewById(R.id.complete_tv_project_name);
            complete_tv_project_company = itemView.findViewById(R.id.complete_tv_project_company);
            complete_tv_project_score = itemView.findViewById(R.id.complete_tv_project_score);
            complete_tv_project_address = itemView.findViewById(R.id.complete_tv_project_address);
            complete_tv_update_time = itemView.findViewById(R.id.complete_tv_update_time);

        }
    }

    public class unCompleteListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView uncomplete_cardView;
        private TextView unComplete_tv_schedule;
        private TextView unComplete_tv_explain;
        private TextView unComplete_tv_project_times;
        private TextView unComplete_tv_project_code;
        private TextView unComplete_tv_project_name;
        private TextView unComplete_tv_project_company;
        private TextView unComplete_tv_project_score;
        private TextView unComplete_tv_project_address;
        private TextView unComplete_tv_update_time;
        private TextView unComplete_item_data;

        public unCompleteListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            uncomplete_cardView = itemView.findViewById(R.id.uncomplete_cardView);
            unComplete_tv_schedule = itemView.findViewById(R.id.unComplete_tv_schedule);
            unComplete_tv_explain = itemView.findViewById(R.id.unComplete_tv_explain);
            unComplete_tv_project_times = itemView.findViewById(R.id.unComplete_tv_project_times);
            unComplete_tv_project_code = itemView.findViewById(R.id.unComplete_tv_project_code);
            unComplete_tv_project_name = itemView.findViewById(R.id.unComplete_tv_project_name);
            unComplete_tv_project_company = itemView.findViewById(R.id.unComplete_tv_project_company);
            unComplete_tv_project_score = itemView.findViewById(R.id.unComplete_tv_project_score);
            unComplete_tv_project_address = itemView.findViewById(R.id.unComplete_tv_project_address);
            unComplete_tv_update_time = itemView.findViewById(R.id.unComplete_tv_update_time);
            unComplete_item_data = itemView.findViewById(R.id.unComplete_item_data);
        }
    }

    public class unDownLoadListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private CardView undownload_cardView;
        private TextView undownload_tv_schedule;
        private TextView undownload_tv_explain;
        private TextView undownload_tv_project_times;
        private TextView undownload_tv_project_code;
        private TextView undownload_tv_project_name;
        private TextView undownload_tv_project_company;
        private TextView undownload_tv_project_address;

        public unDownLoadListHaveBeenCancelViewHolder(final View itemView) {
            super(itemView);
            undownload_cardView = itemView.findViewById(R.id.undownload_cardView);
            undownload_tv_schedule = itemView.findViewById(R.id.undownload_tv_schedule);
            undownload_tv_explain = itemView.findViewById(R.id.undownload_tv_explain);
            undownload_tv_project_times = itemView.findViewById(R.id.undownload_tv_project_times);
            undownload_tv_project_code = itemView.findViewById(R.id.undownload_tv_project_code);
            undownload_tv_project_name = itemView.findViewById(R.id.undownload_tv_project_name);
            undownload_tv_project_company = itemView.findViewById(R.id.undownload_tv_project_company);
            undownload_tv_project_address = itemView.findViewById(R.id.undownload_tv_project_address);
        }
    }

    public interface adapter_offline {
        void newListClick(ProjectsDB projectsDB);

        void newListLongClick(ProjectsDB projectsDB);

        void completeClick(ProjectsDB projectsDB);

        void completeLongClick(ProjectsDB projectsDB, int i);

        void unCompleteClick(ProjectsDB projectsDB);

        void unCompleteLongClick(ProjectsDB projectsDB, int i);

        void unDownLoadClick(ProjectsDB projectsDB);

        void unDownLoadLongClick(ProjectsDB projectsDB);
    }

    /**
     * 获取题目的数据
     */
    private String getItemDate(String id) {
        ;
        String massage = BaseApplication.mContext.getResources().getString(R.string.toast_25);
        int CountAll = 0;
        int dataSize = 0;
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", id);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                for (int i = 0; i < subjectsDBS.size(); i++) {
                    CountAll++;
                    if (subjectsDBS.get(i).getIsComplete() == 1 && subjectsDBS.get(i).getsUploadStatus() == 0) {
//                        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + id + "/" + subjectsDBS.get(i).getNumber() + "_" + subjectsDBS.get(i).getHt_id());
                        List<String> fileList = FileIOUtils.getAllFileList(Constants.SAVE_DIR_PROJECT_Document + id + "/" + subjectsDBS.get(i).getNumber() + "_" + subjectsDBS.get(i).getHt_id());
                        if (fileList != null && fileList.size() > 0) {
                            for (int j = 0; j < fileList.size(); j++) {
                                if (!fileList.get(j).endsWith("txt")) {
                                    dataSize++;
                                }
                            }
                        }
                    }
                }
                String format = BaseApplication.mContext.getResources().getString(R.string.project_list_date_size);
                String format1 = String.format(format, CountAll, dataSize);
                massage = format1;
            } else {

            }
        }
        return massage;
    }
}
