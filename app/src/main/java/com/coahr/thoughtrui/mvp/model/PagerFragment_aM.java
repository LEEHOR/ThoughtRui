package com.coahr.thoughtrui.mvp.model;

import android.os.Environment;

import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.DeleteFileUtil;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWorkAsync;
import com.coahr.thoughtrui.Utils.JDBC.JDBCDeleteListener;
import com.coahr.thoughtrui.Utils.JDBC.JDBCSelectMultiListener;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.StoreSpaceUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

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
    public void getSubject(final String DbProjectId, final int position) {
        DataBaseWorkAsync.DBSelectByTogether_Where(SubjectsDB.class, new JDBCSelectMultiListener() {




            @Override
            public <T> void SelectMulti(List<T> t) {
                if (t != null && t.size() > 0) {
                    subjectsDB = (SubjectsDB) t.get(position);
                    getPresenter().getSubjectSuccess(subjectsDB);
                    subjectsDBId = subjectsDB.getId();
                    getImage(DbProjectId, position);
                    getAnswer(subjectsDBId);
                } else {
                    getPresenter().getSubjectFailure("0");
                    getPresenter().getImageFailure();
                    getPresenter().getAnswerFailure();
                }
            }
        }, "projectsdb_id=?", DbProjectId);

    }

    @Override
    public void getImage(String ht_ProjectId,int position) {

    }

    @Override
    public void getAnswer(int subject_id) {
        DataBaseWorkAsync.DBSelectByTogether_Where(AnswersDB.class, new JDBCSelectMultiListener() {
            @Override
            public <T> void SelectMulti(List<T> t) {
                if (t != null && t.size()>0) {
                    KLog.d("正在查询","答案");
                    getPresenter().getAnswerSuccess((AnswersDB) t.get(0));
                } else {
                    getPresenter().getImageFailure();
                }
            }
        }, "subjectsdb_id=?",String.valueOf(subject_id));
    }

    @Override
    public void DeleteImage(String deleteImagePath) {

    }

    @Override
    public void saveAnswers(String answers, String remark) {
        List<AnswersDB> answersList = subjectsDB.getAnswers();
        if (answersList !=null && answersList.size()>0){ //当前有答案
            if (answers !=null) {
                answersList.get(0).setAnswer(answers);
            }
            if (remark !=null){
                answersList.get(0).setRemakes(remark);
            }
            answersList.get(0).saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    if (success) {
                        getPresenter().saveAnswersSuccess();
                    } else {
                        getPresenter().saveAnswersFailure();
                    }
                }
            });
        } else {
            AnswersDB answersDB=new AnswersDB();
            if (answers !=null){
                answersDB.setAnswer(answers);
            }
            if (remark != null) {
                answersDB.setRemakes(remark);
            }
            answersDB.saveAsync().listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    if (success) {
                        getPresenter().saveAnswersSuccess();
                    } else {
                        getPresenter().saveAnswersFailure();
                    }
                }
            });
        }

    }

    @Override
    public void SaveImages(List<MediaBean> mediaBeanList,String ht_ProjectId,int position) {
        //获取当前题目下的图片
        List<String> picturesList = StoreSpaceUtils.getPictures(Constants.SAVE_DIR_PROJECT_PHOTO + position + "/");
        for (int i = 0; i <mediaBeanList.size() ; i++) {
            String originalPath = mediaBeanList.get(i).getOriginalPath();
            KLog.d("保存"+i,originalPath);
            StoreSpaceUtils.copyFile(originalPath,Constants.SAVE_DIR_PROJECT_PHOTO,StoreSpaceUtils.getE(originalPath,"/"));
        }
    }
}
