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
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 15:50
 * 首页项目adapter
 */
public class MyTabFOnLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeDataList.DataBean.AllListBean> allListBean = new ArrayList<>();
    private Context context;
    private int type;
    // 0 1 2  3 新项目 已完成 未完成  全部
    private int newProject = 0;
    private int complete = 1;
    private int uncomplete = 2;
    private int undownload = 3;
    private int unKnow = 4;
    private adapter_online adapter_online;

    public void setHomeDataList(List<HomeDataList.DataBean.AllListBean> allListBean_a, Context context) {
        allListBean.clear();
        this.allListBean.addAll(allListBean_a);
        this.context = context;
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
            return new unDownLoadListHaveBeenCancelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytabfragment_undownload, viewGroup, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder != null && viewHolder.itemView != null) {
            if (viewHolder instanceof newListHaveBeenCancelViewHolder) {
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_schedule.setText(allListBean.get(i).getProgress());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_explain.setText(allListBean.get(i).getSale_code() != null ? allListBean.get(i).getSale_code() : allListBean.get(i).getService_code());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_times.setText(allListBean.get(i).getPname());
                // ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_code.setText(allListBean.get(i).getCode());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_name.setText(allListBean.get(i).getPname());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_company.setText(allListBean.get(i).getDname());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_project_address.setText(allListBean.get(i).getAreaAddress());
                ((newListHaveBeenCancelViewHolder) viewHolder).new_tv_update_time.setText(TimeUtils.getStringDate_start(allListBean.get(i).getModifyTime()));
                ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.newListClick(allListBean.get(i));
                        }
                    }
                });

                ((newListHaveBeenCancelViewHolder) viewHolder).new_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.newListLongClick(allListBean.get(i));
                        }
                        return false;
                    }
                });
            }
            if (viewHolder instanceof completeListHaveBeenCancelViewHolder) {
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_schedule.setText(allListBean.get(i).getProgress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_explain.setText(allListBean.get(i).getSale_code() != null ? allListBean.get(i).getSale_code() : allListBean.get(i).getService_code());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_times.setText(allListBean.get(i).getPname());
                // ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_code.setText(allListBean.get(i).getCode());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_name.setText(allListBean.get(i).getPname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_company.setText(allListBean.get(i).getDname());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_address.setText(allListBean.get(i).getAreaAddress());
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_update_time.setText(TimeUtils.getStringDate_start(allListBean.get(i).getModifyTime()));

                List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", allListBean.get(i).getId());
                int totalScore = 0;
                if (projectsDBS != null && projectsDBS.size() > 0) {
                    List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
                    if (subjectsDBS != null && subjectsDBS.size() > 0) {
                        totalScore = ReviewScoreUtil.getTotalScore(subjectsDBS);
                        ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_score.setText("检核得分: " + totalScore + "分");
                        KLog.e("lizhiguo", "totalScore == " + totalScore);
                    } else {
                        ((completeListHaveBeenCancelViewHolder) viewHolder).complete_tv_project_score.setText("检核得分: " + allListBean.get(i).getTotalScore() + "分");
                        KLog.e("lizhiguo", "TotalScore == " + allListBean.get(i).getTotalScore());
                    }
                }

                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.completeClick(allListBean.get(i));
                        }
                    }
                });
                ((completeListHaveBeenCancelViewHolder) viewHolder).complete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.completeLongClick(allListBean.get(i), i);
                        }
                        return false;
                    }
                });
            }

            if (viewHolder instanceof unCompleteListHaveBeenCancelViewHolder) {
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_schedule.setText(allListBean.get(i).getProgress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_explain.setText(allListBean.get(i).getSale_code() != null ? allListBean.get(i).getSale_code() : allListBean.get(i).getService_code());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_times.setText(allListBean.get(i).getPname());
                // ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_code.setText(allListBean.get(i).getCode());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_name.setText(allListBean.get(i).getPname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_company.setText(allListBean.get(i).getDname());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_address.setText(allListBean.get(i).getAreaAddress());
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_update_time.setText(TimeUtils.getStringDate_start(allListBean.get(i).getModifyTime()));
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_item_data.setText(getItemDate(allListBean.get(i).getId()));

                List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", allListBean.get(i).getId());
                int totalScore = 0;
                if (projectsDBS != null && projectsDBS.size() > 0) {
                    List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
                    if (subjectsDBS != null && subjectsDBS.size() > 0) {
                        totalScore = ReviewScoreUtil.getTotalScore(subjectsDBS);
                        KLog.e("lizhiguo", "totalScore == " + totalScore);
                        ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_score.setText("检核得分: " + totalScore + "分");
                    } else {
                        ((unCompleteListHaveBeenCancelViewHolder) viewHolder).unComplete_tv_project_score.setText("检核得分: " + allListBean.get(i).getTotalScore() + "分");
                        KLog.e("lizhiguo", "TotalScore == " + allListBean.get(i).getTotalScore());
                    }
                }
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unCompleteClick(allListBean.get(i));
                        }
                    }
                });
                ((unCompleteListHaveBeenCancelViewHolder) viewHolder).uncomplete_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unCompleteLongClick(allListBean.get(i), i);
                        }
                        return false;
                    }
                });

            }

            if (viewHolder instanceof unDownLoadListHaveBeenCancelViewHolder) {
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_schedule.setText(allListBean.get(i).getProgress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_explain.setText(allListBean.get(i).getSale_code() != null ? "[" + allListBean.get(i).getSale_code() + "]" : "[" + allListBean.get(i).getService_code() + "]");
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_times.setText(allListBean.get(i).getPname());
                // ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_code.setText(allListBean.get(i).getCode());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_name.setText(allListBean.get(i).getPname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_company.setText(allListBean.get(i).getDname());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_tv_project_address.setText(allListBean.get(i).getAreaAddress());
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unDownLoadClick(allListBean.get(i));
                        }
                    }
                });
                ((unDownLoadListHaveBeenCancelViewHolder) viewHolder).undownload_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (adapter_online != null) {
                            adapter_online.unDownLoadLongClick(allListBean.get(i));
                        }
                        return false;
                    }
                });
            }

            /*if (viewHolder instanceof EmpViewHolder) {

            }*/
        }
    }

    @Override
    public int getItemCount() {
        if (allListBean != null) {
            return allListBean.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
     /*   if (type == 0) { //新项目
            if (allListBean.get(position).getCompleteStatus() == 1 && allListBean.get(position).getDownloadTime() != -1) {
                return newProject;
            } else if (allListBean.get(position).getCompleteStatus() == 1 && allListBean.get(position).getDownloadTime() == -1) {
                return undownload;
            }
        }*/

        if (type == 0) {  //已完成
            if (allListBean.get(position).getCompleteStatus() == 3) {
                return complete;
            }
        }

        if (type == 1) {  //未完成
            if (allListBean.get(position).getCompleteStatus() == 2 || allListBean.get(position).getCompleteStatus() == 1) {
                return uncomplete;
            }
        }

        if (type == 2) {  //全部
           /* if (allListBean.get(position).getCompleteStatus() == 1 && allListBean.get(position).getDownloadTime() != -1) {//新项目
                return newProject;
            }*/
            if (allListBean.get(position).getCompleteStatus() == 2) {  //未完成
                return uncomplete;
            }
            if (allListBean.get(position).getCompleteStatus() == 3) {  //完成
                return complete;
            }
           /* if (allListBean.get(position).getCompleteStatus() == 1 && allListBean.get(position).getDownloadTime() == -1) { //未下载
                return undownload;
            }*/
        }
        return uncomplete;
    }

    public class newListHaveBeenCancelViewHolder extends RecyclerView.ViewHolder {
        private TextView new_tv_update_message_date;
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
            new_tv_update_message_date = itemView.findViewById(R.id.new_tv_update_message_date);

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
        private TextView unComplete_item_data;
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
            unComplete_item_data = itemView.findViewById(R.id.unComplete_item_data);
        }
    }

    public void setAdapter_online(MyTabFOnLineAdapter.adapter_online adapter_online) {
        this.adapter_online = adapter_online;
    }

    public interface adapter_online {
        void newListClick(HomeDataList.DataBean.AllListBean newListBean);

        void newListLongClick(HomeDataList.DataBean.AllListBean newListBean);

        void completeClick(HomeDataList.DataBean.AllListBean completeListBean);

        void completeLongClick(HomeDataList.DataBean.AllListBean completeListBean, int i);

        void unCompleteClick(HomeDataList.DataBean.AllListBean unCompleteListBean);

        void unCompleteLongClick(HomeDataList.DataBean.AllListBean unCompleteListBean, int i);

        void unDownLoadClick(HomeDataList.DataBean.AllListBean newListBean);

        void unDownLoadLongClick(HomeDataList.DataBean.AllListBean newListBean);
    }

    /**
     * 获取题目的数据
     */
    private String getItemDate(String id) {
        String massage = BaseApplication.mContext.getResources().getString(R.string.toast_25);
        int CountAll = 0;
        int dataSize = 0;
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", id);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                for (int i = 0; i < subjectsDBS.size(); i++) {
                    //已完成，未上传
                    if (subjectsDBS.get(i).getIsComplete() == 1 && subjectsDBS.get(i).getsUploadStatus() == 0) {
                        CountAll++;
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