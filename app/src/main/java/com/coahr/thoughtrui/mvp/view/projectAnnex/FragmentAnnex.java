package com.coahr.thoughtrui.mvp.view.projectAnnex;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.constract.FragmentAnnex_C;
import com.coahr.thoughtrui.mvp.presenter.FragmentAnnex_P;
import com.coahr.thoughtrui.mvp.view.projectAnnex.adapter.AnnexAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/16
 * 描述：项目附件子页
 */
public class FragmentAnnex extends BaseChildFragment<FragmentAnnex_C.Presenter> implements FragmentAnnex_C.View {
    @Inject
    FragmentAnnex_P p;
    @BindView(R.id.annex_recycler)
    RecyclerView annex_recycler;
    @BindView(R.id.annex_swipe)
    SwipeRefreshLayout annex_swipe;
    private AnnexAdapter annexAdapter;
    private LinearLayoutManager manager;
    private String projectId;
    private int position;
    private boolean isLoading;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    private List<ProjectsDB> projectsDBS;
    private List<List<String>> listList;

    public static FragmentAnnex newInstance(int position, String projectId) {
        FragmentAnnex annex = new FragmentAnnex();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("projectId", projectId);
        annex.setArguments(bundle);
        return annex;
    }
    private final static int setList=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case setList:
                    if (listList != null) {

                    }
                    break;
            }
        }
    };

    @Override
    public BaseContract.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_annex;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            projectId = getArguments().getString("projectId");
            position = getArguments().getInt("position");
        }
        annexAdapter = new AnnexAdapter(BaseApplication.mContext);
        manager = new LinearLayoutManager(BaseApplication.mContext);
        annex_recycler.setLayoutManager(manager);
        annex_recycler.setAdapter(annexAdapter);
      /*  annex_recycler.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5), getResources().getColor(R.color.material_grey_200)));
        for (int i = 0; i < annex_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                annex_recycler.removeItemDecorationAt(i);
            }
        }*/
        annex_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    getSubjectList();
                } else {
                    annex_swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void initData() {
        getSubjectList();
        annexAdapter.setAnnexAdapterOnClick(new AnnexAdapter.annexAdapterOnClick() {
            @Override
            public void playAudio(String audioPath) {
                if (audioPath != null) {
                    DialogFragmentAudioPlay audioPlay = DialogFragmentAudioPlay.newInstance(audioPath, FileIOUtils.getE(audioPath, "/"));
                    audioPlay.show(getChildFragmentManager(), TAG);
                } else {
                    ToastUtils.showLong("没有录音文件");
                }
            }

            @Override
            public void BrowserPic(List<String> picList, int position) {
                photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
                photoAlbumDialogFragment.setImgList(picList);
                photoAlbumDialogFragment.setFirstSeePosition(position);
                FragmentManager fragmentManager = getFragmentManager();
                photoAlbumDialogFragment.show(fragmentManager, TAG);
            }
        });
    }

    @Override
    public void getSubjectSuccess(List<SubjectsDB> subjectsDB, ProjectsDB projectsDB) {
        p.getFileList(subjectsDB, projectsDB);
        isLoading = false;
        annex_swipe.setRefreshing(false);
    }

    @Override
    public void getSubjectFailure(String failure) {
        ToastUtils.showLong(failure);
        isLoading = false;
        annex_swipe.setRefreshing(false);
    }

    @Override
    public void getFileListSuccess(final List<List<String>> fileList) {
        annexAdapter.setAdapterList(fileList, position);
        annexAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFileListFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    /**
     * 获取题目列表
     */
    private void getSubjectList() {
        projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);
        if (projectsDBS != null && projectsDBS.size()>0) {
            p.getSubject(projectsDBS.get(0));
        }

    }
}
