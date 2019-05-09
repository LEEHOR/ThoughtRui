package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据展示
 */
public class AnnexDate {

    private List<AnnexFileList> listList=new ArrayList<>();

    public AnnexDate() {
    }

    public List<AnnexFileList> getListList() {
        return listList;
    }

    public void setListList(List<AnnexFileList> listList) {
        this.listList = listList;
    }

    public  static class AnnexFileList{
        private ArrayList<String> listFile=new ArrayList<>();
        private boolean isShow;

        public ArrayList<String> getListFile() {
            return listFile;
        }

        public void setListFile(ArrayList<String> listFile) {
            this.listFile = listFile;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public AnnexFileList() {

        }
    }
}
