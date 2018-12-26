package com.coahr.thoughtrui.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.provider.SyncStateContract;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.StoreSpaceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 23:04
 */
public class PagerFragment_aM extends BaseModel<PagerFragment_aC.Presenter> implements PagerFragment_aC.Model {
    @Inject
    public PagerFragment_aM() {
        super();
    }
    private SubjectsDB subjectsDB;
    private int subjectsDBId;
    @Override
    public void getSubject(final String DbProjectId, final String ht_ProjectId, final int position, Activity activity) {
        KLog.d("刷新",DbProjectId,ht_ProjectId,position);
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=?", DbProjectId);
        if (subjectsDBS != null && subjectsDBS.size() > 0) {
            subjectsDB = (SubjectsDB) subjectsDBS.get(position);
            subjectsDBId = subjectsDB.getId();
            KLog.d("题目Id", subjectsDBId);
            getImage(ht_ProjectId, (position + 1),activity);
            getAnswer(ht_ProjectId,(position + 1),activity);
            getAudio(ht_ProjectId,(position+1),activity);
            getPresenter().getSubjectSuccess(subjectsDB);

        } else {
            getPresenter().getSubjectFailure("0");
            getPresenter().getImageFailure();
            getPresenter().getAnswerFailure();
        }

    }

    @Override
    public void getImage(final String ht_ProjectId, final int position, Activity activity) {
        //获取当前题目下的图片
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> picturesList = FileIOUtils.getPictures(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + position);
                if (picturesList != null) {
                    getPresenter().getImageSuccess(picturesList);
                } else {
                    getPresenter().getImageFailure();
                }
            }
        });

    }

    @Override
    public void getAnswer(final String ht_ProjectId, final int position, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String s = SaveOrGetAnswers.readFromFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + position + "/");
                if (s != null) {
                    getPresenter().getAnswerSuccess(s);
                } else {
                    getPresenter().getAnswerFailure();
                }
            }
        });

    }

    @Override
    public void DeleteImage(String deleteImagePath) {

        boolean b = FileIOUtils.deleteFilePic(deleteImagePath);
        if (b) {
            getPresenter().DeleteImageSuccess("删除图片成功");
        } else {
            getPresenter().DeleteImageFailure("删除图片失败");
        }
    }

    @Override
    public void saveAnswers(String answers, String remark,String ht_ProjectId,int position ) {
        boolean b = SaveOrGetAnswers.saveToFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + position + "/", "1答案:"+answers + "&2备注:"+remark, false);
        if (b) {
            getPresenter().saveAnswersSuccess();
        } else {
            getPresenter().saveAnswersFailure();
        }
    }

    @Override
    public void SaveImages(final List<MediaBean> mediaBeanList, final String ht_ProjectId, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <mediaBeanList.size() ; i++) {
                    String originalPath = mediaBeanList.get(i).getOriginalPath();
                    FileIOUtils.copyFile(originalPath, Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + position + "/", FileIOUtils.getE(originalPath, "/"));
                }
            }
        }).start();


    }

    @Override
    public void getAudio(final String ht_ProjectId, final int position, Activity activity) {
        //获取当前题目下的图片
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> audiosList = FileIOUtils.getAudios(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + position);
                if (audiosList != null) {
                    getPresenter().getAudioSuccess(audiosList);
                } else {
                    getPresenter().getAudioFailure("没有录音");
                }
            }
        });
    }
}
