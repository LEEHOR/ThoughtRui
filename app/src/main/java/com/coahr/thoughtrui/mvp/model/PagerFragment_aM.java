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
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

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


    @Override
    public void getSubject(String DbProjectId, final int position) {
        DataBaseWorkAsync.DBSelectByTogether_Where(SubjectsDB.class, new JDBCSelectMultiListener() {
            @Override
            public <T> void SelectMulti(List<T> t) {
                if (t != null && t.size() > 0) {
                    SubjectsDB subjectsDB = (SubjectsDB) t.get(position);
                    KLog.d("正在查询",subjectsDB.getHt_id());
                    getPresenter().getSubjectSuccess(subjectsDB);
                    int id = subjectsDB.getId();
                    getImage(id);
                    getAnswer(id);
                } else {
                    getPresenter().getSubjectFailure("0");
                }
            }
        }, "projectsdb_id=?", DbProjectId);

    }

    @Override
    public void getImage(int subject_id) {
        DataBaseWorkAsync.DBSelectByTogether_Where(ImagesDB.class, new JDBCSelectMultiListener() {
            @Override
            public <T> void SelectMulti(List<T> t) {
                if (t != null && t.size()>0) {
                    KLog.d("正在查询","图片");
                    getPresenter().getImageSuccess((List<ImagesDB>) t);
                } else {
                    getPresenter().getImageFailure();
                }
            }
        }, "subjectsdb_id=?",String.valueOf(subject_id));
    }

    @Override
    public void getAnswer(int subject_id) {
        DataBaseWorkAsync.DBSelectByTogether_Where(AnswersDB.class, new JDBCSelectMultiListener() {
            @Override
            public <T> void SelectMulti(List<T> t) {
                if (t != null && t.size()>0) {
                    KLog.d("正在查询","答案");
                    getPresenter().getAnswerSuccess((List<AnswersDB>) t);
                } else {
                    getPresenter().getImageFailure();
                }
            }
        }, "subjectsdb_id=?",String.valueOf(subject_id));
    }

    @Override
    public void DeleteImage(int imageId, final String imageName) {
            DataBaseWorkAsync.DBDeleteById(ImagesDB.class, imageId, new JDBCDeleteListener() {
                @Override
                public void delete(int i) {
                    if (i>0){
                        // 找到文件所在的路径并删除该文件
                        boolean delete = DeleteFileUtil.deleteFile(imageName);
                        if (delete){
                            getPresenter().DeleteImageSuccess(imageName);
                        } else {
                            getPresenter().DeleteImageFailure(imageName);
                        }
                    } else {
                        getPresenter().DeleteImageFailure(imageName);
                    }
                }
            });
    }
}
