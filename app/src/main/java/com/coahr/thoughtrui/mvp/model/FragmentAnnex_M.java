package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.FragmentAnnex_C;
import com.coahr.thoughtrui.mvp.model.Bean.AnnexDate;

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
    private AnnexDate annexDate=new AnnexDate();

    @Override
    public void getSubject(ProjectsDB projectsDB) {
        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectBy_order(SubjectsDB.class,new String[]{"projectsdb_id=?", String.valueOf(projectsDB.getId())},"number","ht_id","number");
        if (subjectsDBList != null && subjectsDBList.size()>0) {
            getPresenter().getSubjectSuccess(subjectsDBList,projectsDB);
        }else {
            getPresenter().getSubjectFailure("暂无题目");
        }
    }

    @Override
    public void getFileList(final List<SubjectsDB> subjectsDBList, final ProjectsDB projectsDB) {
        annexDate.getListList().clear();
                    for (int i = 0; i < subjectsDBList.size(); i++) {
                        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB.getPid() + "/" + subjectsDBList.get(i).getNumber() + "_" + subjectsDBList.get(i).getHt_id());
                        AnnexDate.AnnexFileList annexFileList=new AnnexDate.AnnexFileList();
                        annexFileList.setShow(false);
                        if (fileList != null && fileList.size()>0) {
                            for (int j = 0; j <fileList.size() ; j++) {
                                annexFileList.getListFile().add(fileList.get(j));
                            }
                        }
                        annexDate.getListList().add(annexFileList);
                    }
                    if (annexDate !=null ) {
                        getPresenter().getFileListSuccess(annexDate);
                    } else {
                        getPresenter().getFileListFailure("没有数据");
                    }

    }
}
