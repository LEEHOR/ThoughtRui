package com.coahr.thoughtrui.mvp.view.projectAnnex.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
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
public class AnnexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<String>> listList = new ArrayList<>();
    private int type;
    private Context context;
    private annexAdapterOnClick annexAdapterOnClick;
    private List<List<String>> zkList = new ArrayList<>();
    private List<String> listPic;

    public AnnexAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterList(List<List<String>> list, int types) {
        listList.clear();
        this.listList.addAll(list);
        this.type = types;
        getItemCount();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == 0) {
            return new MyAdapter_q(LayoutInflater.from(context).inflate(R.layout.item_fragment_annex_q, viewGroup, false));
        } else if (i == 1) {
            return new MyAdapter_t(LayoutInflater.from(context).inflate(R.layout.item_fragment_annex_t, viewGroup, false));
        } else if (i == 2) {
            return new MyAdapter_l(LayoutInflater.from(context).inflate(R.layout.item_fragment_annex_l, viewGroup, false));
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof MyAdapter_q) {
            String audioPath = null;
            if (listList != null && listList.size() > 0) {
                ((MyAdapter_q) viewHolder).annex_tv_tittle_q.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_16) + String.valueOf(i + 1));
                final List<String> listPath = listList.get(i);
                if (listPath != null && listPath.size() > 0) {
                    listPic = new ArrayList<>();
                    for (int j = 0; j < listPath.size(); j++) {
                        if (listPath.get(j).endsWith("wav")) {
                            audioPath = listPath.get(j);

                        }
                        if (!listPath.get(j).endsWith("wav") && !listPath.get(j).endsWith("txt")) {
                            listPic.add(listPath.get(j));
                        }
                    }
                } else {
                    audioPath = null;

                    listPic = new ArrayList<>();
                }


                if (audioPath != null) {
                    ((MyAdapter_q) viewHolder).audio_name.setText(FileIOUtils.getE(audioPath, "/"));
                } else {
                    ((MyAdapter_q) viewHolder).audio_name.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_17));
                }

                final String finalAudioPath = audioPath;
                ((MyAdapter_q) viewHolder).audio_paly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (annexAdapterOnClick != null) {
                            annexAdapterOnClick.playAudio(finalAudioPath);
                        }
                    }
                });
                if (listPic.size() > 0) {
                    String format = BaseApplication.mContext.getResources().getString(R.string.project_annex_pic_count);
                    String count = String.format(format, listPic.size());
                    ((MyAdapter_q) viewHolder).pic_count.setText(count);
                    PagerFragmentPhotoAdapter adapter = new PagerFragmentPhotoAdapter();
                    adapter.setNewData(listPic);
                    adapter.setImageList(listPic);
                    adapter.notifyDataSetChanged();
                    adapter.setListener(new PagerFragmentPhotoListener() {
                        @Override
                        public void onClick(List<String> imagePathList, int position) {
                            if (annexAdapterOnClick != null) {
                                annexAdapterOnClick.BrowserPic(imagePathList, position);
                            }
                        }

                        @Override
                        public void onLongClick(List<String> imagePathList, int position) {

                        }

                        @Override
                        public void onDel(List<String> imagePathList, int position) {

                        }
                    });
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setLayoutManager(gridLayoutManager);
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setAdapter(adapter);
                    ((MyAdapter_q) viewHolder).annex_child_recycler.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 5), DensityUtils.dp2px(BaseApplication.mContext, 5)), context.getResources().getColor(R.color.material_grey_200));
                    for (int k = 0; k < ((MyAdapter_q) viewHolder).annex_child_recycler.getItemDecorationCount(); k++) {
                        if (k != 0) {
                            ((MyAdapter_q) viewHolder).annex_child_recycler.removeItemDecorationAt(k);
                        }
                    }
                } else {
                    PagerFragmentPhotoAdapter adapter = new PagerFragmentPhotoAdapter();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
                    adapter.setImageList(listPic);
                    adapter.setNewData(listPic);
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setLayoutManager(gridLayoutManager);
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setAdapter(adapter);
                    ((MyAdapter_q) viewHolder).pic_count.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_18));
                }
                //   ((MyAdapter_q) viewHolder).iv_zk.setTag(String.valueOf(i));
                ((MyAdapter_q) viewHolder).image_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //展开
                        if (!zkList.contains(listList.get(i))) {
                            ((MyAdapter_q) viewHolder).annex_child_recycler.setVisibility(View.VISIBLE);
                            ((MyAdapter_q) viewHolder).iv_zk.setImageResource(R.mipmap.back);
                            ((MyAdapter_q) viewHolder).iv_zk.setRotation(-90);
                            zkList.add(listList.get(i));
                        } else {
                            ((MyAdapter_q) viewHolder).annex_child_recycler.setVisibility(View.GONE);
                            ((MyAdapter_q) viewHolder).iv_zk.setImageResource(R.mipmap.back);
                            ((MyAdapter_q) viewHolder).iv_zk.setRotation(90);
                            zkList.remove(listList.get(i));
                        }
                    }
                });

                if (zkList.contains(listList.get(i))) {
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setVisibility(View.VISIBLE);
                    ((MyAdapter_q) viewHolder).iv_zk.setImageResource(R.mipmap.back);
                    ((MyAdapter_q) viewHolder).iv_zk.setRotation(-90);
                } else {
                    ((MyAdapter_q) viewHolder).annex_child_recycler.setVisibility(View.GONE);
                    ((MyAdapter_q) viewHolder).iv_zk.setImageResource(R.mipmap.back);
                    ((MyAdapter_q) viewHolder).iv_zk.setRotation(90);
                }
            }

        }


        if (viewHolder instanceof MyAdapter_t) {
            if (listList != null && listList.size() > 0) {

                ((MyAdapter_t) viewHolder).annex_tv_tittle_t.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_16) + String.valueOf(i + 1));
                final List<String> listPath = listList.get(i);
                if (listPath != null && listPath.size() > 0) {
                    listPic = new ArrayList<>();
                    for (int j = 0; j < listPath.size(); j++) {
                        if (!listPath.get(j).endsWith("wav") && !listPath.get(j).endsWith("txt")) {
                            listPic.add(listPath.get(j));
                        }
                    }
                } else {
                    listPic = new ArrayList<>();
                }
                if (listPic.size() > 0) {
                    String format = BaseApplication.mContext.getResources().getString(R.string.project_annex_pic_count);
                    String count = String.format(format, listPic.size());
                    ((MyAdapter_t) viewHolder).annex_pic_count_t.setText(count);
                    PagerFragmentPhotoAdapter adapter = new PagerFragmentPhotoAdapter();
                    adapter.setNewData(listPic);
                    adapter.setImageList(listPic);
                    adapter.setListener(new PagerFragmentPhotoListener() {
                        @Override
                        public void onClick(List<String> imagePathList, int position) {
                            if (annexAdapterOnClick != null) {
                                annexAdapterOnClick.BrowserPic(imagePathList, position);
                            }
                        }

                        @Override
                        public void onLongClick(List<String> imagePathList, int position) {

                        }

                        @Override
                        public void onDel(List<String> imagePathList, int position) {

                        }
                    });
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setLayoutManager(gridLayoutManager);
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setAdapter(adapter);
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 5), DensityUtils.dp2px(BaseApplication.mContext, 5)), context.getResources().getColor(R.color.material_grey_200));
                    for (int k = 0; k < ((MyAdapter_t) viewHolder).annex_child_recycler_t.getItemDecorationCount(); k++) {
                        if (k != 0) {
                            ((MyAdapter_t) viewHolder).annex_child_recycler_t.removeItemDecorationAt(k);
                        }
                    }
                } else {
                    PagerFragmentPhotoAdapter adapter = new PagerFragmentPhotoAdapter();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
                    adapter.setNewData(listPic);
                    adapter.setImageList(listPic);
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setLayoutManager(gridLayoutManager);
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setAdapter(adapter);
                    ((MyAdapter_t) viewHolder).annex_pic_count_t.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_18));
                }
                ((MyAdapter_t) viewHolder).annex_pic_re_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //展开
                        if (!zkList.contains(listList.get(i))) {
                            ((MyAdapter_t) viewHolder).annex_child_recycler_t.setVisibility(View.VISIBLE);
                            ((MyAdapter_t) viewHolder).iv_zk_t.setImageResource(R.mipmap.back);
                            ((MyAdapter_t) viewHolder).iv_zk_t.setRotation(-90);
                            zkList.add(listList.get(i));
                        } else {
                            ((MyAdapter_t) viewHolder).annex_child_recycler_t.setVisibility(View.GONE);
                            ((MyAdapter_t) viewHolder).iv_zk_t.setImageResource(R.mipmap.back);
                            ((MyAdapter_t) viewHolder).iv_zk_t.setRotation(90);
                            zkList.remove(listList.get(i));
                        }
                    }
                });
                if (zkList.contains(listList.get(i))) {
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setVisibility(View.VISIBLE);
                    ((MyAdapter_t) viewHolder).iv_zk_t.setImageResource(R.mipmap.back);
                    ((MyAdapter_t) viewHolder).iv_zk_t.setRotation(-90);
                } else {
                    ((MyAdapter_t) viewHolder).annex_child_recycler_t.setVisibility(View.GONE);
                    ((MyAdapter_t) viewHolder).iv_zk_t.setImageResource(R.mipmap.back);
                    ((MyAdapter_t) viewHolder).iv_zk_t.setRotation(90);
                }
            }
        }


        if (viewHolder instanceof MyAdapter_l) {
            if (listList != null && listList.size() > 0) {
                ((MyAdapter_l) viewHolder).annex_tv_tittle_l.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_16) + String.valueOf(i + 1));
                final List<String> listPath = listList.get(i);
                String audioPath = null;
                if (listPath != null && listPath.size() > 0) {
                    for (int j = 0; j < listPath.size(); j++) {
                        if (listPath.get(j).endsWith("wav")) {
                            audioPath = listPath.get(j);
                        }
                    }
                } else {
                    audioPath = null;
                }

                if (audioPath != null) {
                    ((MyAdapter_l) viewHolder).annex_audio_name_l.setText(FileIOUtils.getE(audioPath, "/"));
                } else {
                    ((MyAdapter_l) viewHolder).annex_audio_name_l.setText(BaseApplication.mContext.getResources().getString(R.string.phrases_17));
                }

                final String finalAudioPath = audioPath;
                ((MyAdapter_l) viewHolder).annex_audio_play_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (annexAdapterOnClick != null) {
                            annexAdapterOnClick.playAudio(finalAudioPath);
                        }
                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public class MyAdapter_q extends RecyclerView.ViewHolder {
        private final ImageView iv_zk;
        private final RecyclerView annex_child_recycler;
        private final TextView pic_count;
        private TextView audio_paly;
        private TextView audio_name;
        private RelativeLayout image_re;
        private final TextView annex_tv_tittle_q;

        public MyAdapter_q(View itemView) {
            super(itemView);
            annex_tv_tittle_q = itemView.findViewById(R.id.annex_tv_tittle_q);
            audio_name = itemView.findViewById(R.id.annex_audio_name);
            audio_paly = itemView.findViewById(R.id.annex_audio_play);
            iv_zk = itemView.findViewById(R.id.iv_zk);
            image_re = itemView.findViewById(R.id.image_re);
            pic_count = itemView.findViewById(R.id.annex_pic_count);
            annex_child_recycler = itemView.findViewById(R.id.annex_child_recycler);

        }
    }

    public class MyAdapter_t extends RecyclerView.ViewHolder {
        private final ImageView iv_zk_t;
        private final RecyclerView annex_child_recycler_t;
        private final TextView annex_pic_count_t;
        private final TextView annex_tv_tittle_t;
        private final RelativeLayout annex_pic_re_image;

        public MyAdapter_t(View itemView) {
            super(itemView);
            annex_tv_tittle_t = itemView.findViewById(R.id.annex_tv_tittle_t);
            iv_zk_t = itemView.findViewById(R.id.iv_zk_t);
            annex_pic_re_image= itemView.findViewById(R.id.annex_pic_re_image);
            annex_pic_count_t = itemView.findViewById(R.id.annex_pic_count_t);
            annex_child_recycler_t = itemView.findViewById(R.id.annex_child_recycler_t);
        }
    }

    public class MyAdapter_l extends RecyclerView.ViewHolder {

        private TextView annex_tv_tittle_l;
        private TextView annex_audio_play_l;
        private TextView annex_audio_name_l;

        public MyAdapter_l(View itemView) {
            super(itemView);
            annex_tv_tittle_l = itemView.findViewById(R.id.annex_tv_tittle_l);
            annex_audio_name_l = itemView.findViewById(R.id.annex_audio_name_l);
            annex_audio_play_l = itemView.findViewById(R.id.annex_audio_play_l);
        }
    }

    public interface annexAdapterOnClick {
        void playAudio(String audioPath);

        void BrowserPic(List<String> picList, int position);

    }

    public void setAnnexAdapterOnClick(AnnexAdapter.annexAdapterOnClick annexAdapterOnClick) {
        this.annexAdapterOnClick = annexAdapterOnClick;
    }
}
