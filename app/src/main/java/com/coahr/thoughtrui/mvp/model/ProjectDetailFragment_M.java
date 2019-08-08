package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ProjectDetailFragment_C;
import com.coahr.thoughtrui.mvp.constract.ProjectSuccessDialog_C;
import com.coahr.thoughtrui.mvp.model.Bean.ProjectDetail;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：
 */
public class ProjectDetailFragment_M extends BaseModel<ProjectDetailFragment_C.Presenter> implements ProjectDetailFragment_C.Model {
    @Inject
    public ProjectDetailFragment_M() {
        super();
    }
   private List<SubjectsDB> list =new ArrayList<>();
    @Override
    public void getSubjectList(ProjectsDB projectsDB) {

        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=? ", String.valueOf(projectsDB.getId()));
        list.clear();
        if (subjectsDBList != null && subjectsDBList.size()>0) {

            for (int i = 0; i < subjectsDBList.size(); i++) {
                //本地完成  /*，且上传成功*/
                if (subjectsDBList.get(i).getIsComplete()==1 /*&& subjectsDBList.get(i).getsUploadStatus() == 1*/){
                    list.add(subjectsDBList.get(i));
                }
            }
        }
        getPresenter().getSubjectListSuccess(list,projectsDB,subjectsDBList.size());
    }

    @Override
    public void getDateSize(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB) {
        int unComplete=0;
        int file_lists=0;
        KLog.e("测试代码", "subjectsDBList.size() == " + subjectsDBList.size());
        for (int i = 0; i < subjectsDBList.size(); i++) {
            unComplete++;
            //未上传
            if (subjectsDBList.get(i).getIsComplete() == 1 && subjectsDBList.get(i).getsUploadStatus() == 0){
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
        getPresenter().getDateSizeSuccess(unComplete,file_lists);
        KLog.e("测试代码", "unComplete == " + unComplete + " -- file_lists == " + file_lists);
    }

    @Override
    public void getProjectDetail(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<ProjectDetail>(getApiService().project_detail(map)))
                .subscribeWith(new SimpleDisposableSubscriber<ProjectDetail>() {
                    @Override
                    public void _onNext(ProjectDetail projectDetail) {
                        if (getPresenter() != null) {
                            if (projectDetail.getStatus()==1) {
                                getPresenter().getProjectDetailSuccess(projectDetail);
                            } else {
                                getPresenter().getProjectDetailFailure(projectDetail.getStatus()+"");
                            }
                        }
                    }
                }));
    }
}
