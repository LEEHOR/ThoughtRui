package com.coahr.thoughtrui.DBbean;

import org.litepal.crud.DataSupport;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:26
 */
public class ImagesDB extends DataSupport {
    /**Imageid 图片Id
     * imageName  图片名
     * imagePath  图片源地址
     *zibImagePath  压缩后图片保存地址
     */
    private int id;
    private String imageName;
    private String imagePath;
    private String zibImagePath;
    private SubjectsDB SubjectsDB;

    public ImagesDB() {
    }

    public ImagesDB(int id, String imageName, String imagePath, String zibImagePath, com.coahr.thoughtrui.DBbean.SubjectsDB subjectsDB) {
        this.id = id;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.zibImagePath = zibImagePath;
        SubjectsDB = subjectsDB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getZibImagePath() {
        return zibImagePath;
    }

    public void setZibImagePath(String zibImagePath) {
        this.zibImagePath = zibImagePath;
    }

    public com.coahr.thoughtrui.DBbean.SubjectsDB getSubjectsDB() {
        return SubjectsDB;
    }

    public void setSubjectsDB(com.coahr.thoughtrui.DBbean.SubjectsDB subjectsDB) {
        SubjectsDB = subjectsDB;
    }

}
