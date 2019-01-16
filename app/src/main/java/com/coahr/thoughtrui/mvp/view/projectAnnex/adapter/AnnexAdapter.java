package com.coahr.thoughtrui.mvp.view.projectAnnex.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：
 */
public class AnnexAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<String>> listList;
    private int type;
    private Context context;
    private annexAdapterOnClick annexAdapterOnClick;
    private String audioPath;

    public AnnexAdapter(Context context) {
        this.context = context;
    }
    public void setAdapterList(List<List<String>> list,int types){
        this.listList=list;
        this.type=types;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.item_fragment_annex, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyAdapter) {
            if (type==0){  //全部
                ((MyAdapter) viewHolder).audio_re.setVisibility(View.VISIBLE);
                ((MyAdapter) viewHolder).audio_images.setVisibility(View.VISIBLE);
            } else if (type==1){ //图片
                ((MyAdapter) viewHolder).audio_re.setVisibility(View.GONE);
                ((MyAdapter) viewHolder).audio_images.setVisibility(View.VISIBLE);
            } else if (type==2){ //录音
                ((MyAdapter) viewHolder).audio_re.setVisibility(View.VISIBLE);
                ((MyAdapter) viewHolder).audio_images.setVisibility(View.GONE);
            } else {
                ((MyAdapter) viewHolder).audio_re.setVisibility(View.VISIBLE);
                ((MyAdapter) viewHolder).audio_images.setVisibility(View.VISIBLE);
            }

            if (listList  !=null && listList.size()>0) {
            List<String> listPath = listList.get(i);
                audioPath = null;
            if (listPath !=null && listPath.size()>0) {
                List<String> picList=new ArrayList<>();
                for (int j = 0; j <listPath.size() ; j++) {
                    if (listPath.get(j).endsWith("wav") || listPath.get(j).endsWith("arm") || listPath.get(j).endsWith("mp3")) {
                        audioPath =listPath.get(j);
                    } else if (listPath.get(j).endsWith("txt")){

                    } else {
                        picList.add(listPath.get(j));
                    }
                }
                if (audioPath !=null && ((MyAdapter) viewHolder).audio_name.getVisibility()==View.VISIBLE){
                        ((MyAdapter) viewHolder).audio_name.setText(SaveOrGetAnswers.getString(audioPath,"/"));

                } else {
                        ((MyAdapter) viewHolder).audio_name.setText("无录音");

                }
                ((MyAdapter) viewHolder).audio_paly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (annexAdapterOnClick != null) {
                            annexAdapterOnClick.playAudio(audioPath);
                        }
                    }
                });
                if (picList.size()>0 && ((MyAdapter) viewHolder).audio_images.getVisibility()==View.VISIBLE) {
                    ((MyAdapter) viewHolder).pic_count.setText("共"+picList.size()+"张");
                    PagerFragmentPhotoAdapter adapter=new PagerFragmentPhotoAdapter();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
                    adapter.setImageList(picList);
                    adapter.setListener(new PagerFragmentPhotoListener() {
                        @Override
                        public void onClick(List<String> imagePathList, int position) {
                            if (annexAdapterOnClick != null) {
                                annexAdapterOnClick.BrowserPic(imagePathList,position);
                            }
                        }

                        @Override
                        public void onLongClick(List<String> imagePathList, int position) {

                        }

                        @Override
                        public void onDel(List<String> imagePathList, int position) {

                        }
                    });
                    ((MyAdapter) viewHolder).annex_child_recycler.setLayoutManager(gridLayoutManager);
                    ((MyAdapter) viewHolder).annex_child_recycler.setAdapter(adapter);
                } else {
                    ((MyAdapter) viewHolder).pic_count.setText("无图片");
                }
            }
            }

        }
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }
    public class  MyAdapter extends RecyclerView.ViewHolder {
        private final ImageView iv_zk;
        private final RecyclerView annex_child_recycler;
        private final TextView pic_count;
        private  TextView audio_paly;
        private  TextView audio_name;
        private RelativeLayout audio_re;
        private RelativeLayout audio_images;

        public MyAdapter(View itemView) {
            super(itemView);
            audio_re= itemView.findViewById(R.id.audio_re);
            audio_images=itemView.findViewById(R.id.audio_images);
            audio_name = itemView.findViewById(R.id.annex_audio_name);
            audio_paly = itemView.findViewById(R.id.annex_audio_play);
            iv_zk = itemView.findViewById(R.id.iv_zk);
            pic_count = itemView.findViewById(R.id.annex_pic_count);
            annex_child_recycler = itemView.findViewById(R.id.annex_child_recycler);

        }
    }
public interface  annexAdapterOnClick{
        void playAudio(String audioPath);
        void BrowserPic(List<String> picList,int position);
    }

    public void setAnnexAdapterOnClick(AnnexAdapter.annexAdapterOnClick annexAdapterOnClick) {
        this.annexAdapterOnClick = annexAdapterOnClick;
    }
}
