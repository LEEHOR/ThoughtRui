package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ProjectSuccessDialog_C;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：
 */
public class ProjectSuccessDialog_M extends BaseModel<ProjectSuccessDialog_C.Presenter> implements ProjectSuccessDialog_C.Model {
    @Inject
    public ProjectSuccessDialog_M() {
        super();
    }
   private List<SubjectsDB> list;
    @Override
    public void getSubjectList(ProjectsDB projectsDB) {

        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=? ", String.valueOf(projectsDB.getId()));
        if (subjectsDBList != null && subjectsDBList.size()>0) {
            list =new ArrayList<>();
            for (int i = 0; i < subjectsDBList.size(); i++) {
                if (subjectsDBList.get(i).getIsComplete()==1){
                    list.add(subjectsDBList.get(i));
                }
            }
        }
        if (list!=null && list.size()>0){
            getPresenter().getSubjectListSuccess(list,projectsDB,subjectsDBList.size());
        } else {
            getPresenter().getSubjectListFailure("暂无题目");
        }
    }

    @Override
    public void getDateSize(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB) {
        int unComplete=0;
        int file_lists=0;
        for (int i = 0; i < subjectsDBList.size(); i++) {
            if (subjectsDBList.get(i).getsUploadStatus()==0){
                unComplete++;
//                List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB.getPid() + "/" + subjectsDBList.get(i).getNumber() + "_" + subjectsDBList.get(i).getHt_id());
                List<String> fileList = FileIOUtils.getAllFileList(Constants.SAVE_DIR_PROJECT_Document + projectsDB.getPid() + "/" + subjectsDBList.get(i).getNumber() + "_" + subjectsDBList.get(i).getHt_id());
                if (fileList != null && fileList.size()>0) {
                    for (int j = 0; j <fileList.size() ; j++) {
                        if (!fileList.get(j).endsWith("txt")){
                            file_lists++;
                        }
                    }
                }
            }
        }
        if (unComplete!=0 && file_lists!=0){
            getPresenter().getDateSizeSuccess(unComplete,file_lists);
        } else {
            getPresenter().getDateSizeFailure("暂无附件");
        }
    }
}
