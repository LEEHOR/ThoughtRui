package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.FragmentAnnex_C;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：
 */
public class FragmentAnnex_M extends BaseModel<FragmentAnnex_C.Presenter> implements FragmentAnnex_C.Model {



    @Inject
    public FragmentAnnex_M() {
        super();
    }
  private   List<List<String>> listList=new ArrayList<>();

    @Override
    public void getSubject(ProjectsDB projectsDB) {
        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectBy_Where(SubjectsDB.class,new String[]{"ht_id","number"},"projectsdb_id=?", String.valueOf(projectsDB.getId()));
        if (subjectsDBList != null && subjectsDBList.size()>0) {
            getPresenter().getSubjectSuccess(subjectsDBList,projectsDB);
        }else {
            getPresenter().getSubjectFailure("暂无题目");
        }
    }

    @Override
    public void getFileList(final List<SubjectsDB> subjectsDBList, final ProjectsDB projectsDB) {
        listList.clear();
                    for (int i = 0; i < subjectsDBList.size(); i++) {
                        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB.getPid() + "/" + subjectsDBList.get(i).getNumber() + "_" + subjectsDBList.get(i).getHt_id());
                        List<String>  stringList = new ArrayList<>();
                        if (fileList != null && fileList.size()>0) {
                            for (int j = 0; j <fileList.size() ; j++) {
                                stringList.add(fileList.get(j)) ;
                            }
                        }
                        listList.add(stringList);
                    }
                    if (listList.size()>0) {
                        getPresenter().getFileListSuccess(listList);
                    } else {
                        getPresenter().getFileListFailure("没有数据");
                    }

    }
}
