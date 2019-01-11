package com.coahr.thoughtrui.mvp.model;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ReViewStart_C;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
public class ReViewStart_M extends BaseModel<ReViewStart_C.Presenter> implements ReViewStart_C.Model {
    @Inject
    public ReViewStart_M() {
        super();
    }

    @Override
    public void getSubject(String DbProjectId, String ht_ProjectId, Activity activity, int number, String ht_id) {
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "ht_id=?", ht_id);
        if (subjectsDBS != null && subjectsDBS.size() > 0) {
            KLog.d("题目Id", subjectsDBS.get(0).getHt_id());
            getImage(ht_ProjectId,activity,number,ht_id);
            getAnswer(ht_ProjectId,activity,number,ht_id);
            getAudio(ht_ProjectId,activity,number,ht_id);
            getPresenter().getSubjectSuccess(subjectsDBS.get(0));
        } else {
            getPresenter().getSubjectFailure("0");
            getPresenter().getImageFailure();
            getPresenter().getAnswerFailure();
        }
    }

    @Override
    public void getImage(final String ht_ProjectId, Activity activity, final int number, final String ht_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> picturesList = FileIOUtils.getPictures(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number+"_"+ht_id);
                if (picturesList != null && picturesList.size()>0) {
                    if (getPresenter() != null) {
                        getPresenter().getImageSuccess(picturesList);
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().getImageFailure();
                    }
                }
            }
        }).start();

    }

    @Override
    public void getAnswer(String ht_ProjectId, Activity activity, int number, String ht_id) {
        String s = SaveOrGetAnswers.readFromFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number+"_"+ht_id+"/" );
        if (s != null) {
            getPresenter().getAnswerSuccess(s);
        } else {
            getPresenter().getAnswerFailure();
        }

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
    public void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id) {
        boolean b = SaveOrGetAnswers.saveToFile(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number+"_"+ht_id + "/", "1答案:"+answers + "&2备注:"+remark, false);
        if (b) {
            getPresenter().saveAnswersSuccess();
        } else {
            getPresenter().saveAnswersFailure();
        }
    }

    @Override
    public void SaveImages(final List<MediaBean> mediaBeanList, final String ht_ProjectId, final int number, final String ht_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <mediaBeanList.size() ; i++) {
                    String originalPath = mediaBeanList.get(i).getOriginalPath();
                    FileIOUtils.copyFile(originalPath, Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number+"_"+ht_id + "/", FileIOUtils.getE(originalPath, "/"));
                }
            }
        }).start();
    }

    @Override
    public void getAudio(String ht_ProjectId, Activity activity, int number, String ht_id) {
        List<String> audiosList = FileIOUtils.getAudios(Constants.SAVE_DIR_PROJECT_Document + ht_ProjectId + "/" + number+"_"+ht_id);
        if (audiosList != null) {
            getPresenter().getAudioSuccess(audiosList);
        } else {
            getPresenter().getAudioFailure("没有录音");
        }
    }
}
