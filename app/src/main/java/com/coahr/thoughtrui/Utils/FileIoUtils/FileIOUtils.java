package com.coahr.thoughtrui.Utils.FileIoUtils;

import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/21
 * on 15:18
 */
public class FileIOUtils {
    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     * @Param name      String   复制后名字
     */
    public static boolean copyFile(String oldPath, String newPath, String name) {
        boolean Success = false;
        if (oldPath == null || newPath == null) {
            return false;
        }
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                File newRootFile = new File(newPath); //判断目录是否存在
                if (!newRootFile.exists()) {
                    newRootFile.mkdirs();
                }
                File newfile = new File(newPath + name);
                if (!newfile.exists()) {        //如果目标文件不存在就开始复制
                    InputStream inStream = new FileInputStream(oldPath); //读入原文件
                    FileOutputStream fs = new FileOutputStream(newfile);
                    byte[] buffer = new byte[1444];
                    while ((byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                    int i = oldPath.lastIndexOf("/");
                    String substring = oldPath.substring(0, i + 1);
                    if (substring.equals(Constants.SAVE_DIR_TAKE_PHOTO)) { //如果是做题时图片为相机拍摄的就删除（即地址为takePhoto/开头）
                        deleteFile(oldfile);
                    }
                    KLog.d("截取的地址", substring);
                    return true;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            KLog.e(e.toString());
            return false;
        }
        return false;
    }


    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }


    /**
     * 根据文件路径拷贝文件
     *
     * @param src      源文件
     * @param destPath 目标文件路径
     * @return boolean 成功true、失败false
     */
    public static boolean copyFile(File src, String destPath, String FileName) {
        boolean result = false;
        if ((src == null) || (destPath == null)) {
            return result;
        }
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        File haveFile = new File(destPath + FileName);
        if (!haveFile.exists()) {
            FileChannel srcChannel = null;
            FileChannel dstChannel = null;
            try {
                srcChannel = new FileInputStream(src).getChannel();
                dstChannel = new FileOutputStream(destPath + FileName).getChannel();
                srcChannel.transferTo(0, srcChannel.size(), dstChannel);
                result = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return result;
            }
            try {
                srcChannel.close();
                dstChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }


        return result;
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 得到后缀或文件名或字符串
     *
     * @param path
     * @param flag
     * @return
     */
    public static String getE(String path, String flag) {
        int i = path.lastIndexOf(flag);
        String substring_name = path.substring(i + 1);
        return substring_name;
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static boolean deleteFilePic(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            boolean delete = file.delete();
            if (delete) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 读取文件夹文件（图片）
     * @param strPath
     * @return
     */
    public static List<String> getPictures(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        if (!file.exists()) {
            return null;
        }
        File[] allFiles = file.listFiles();
        if (allFiles == null) {
            return null;
        }

        for (int k = 0; k < allFiles.length; k++) {
            final File fi = allFiles[k];
            if (fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }

    /**
     * 读取文件夹文件（录音）
     * @param strPath
     * @return
     */
    public static List<String> getAudios(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        if (!file.exists()) {
            return null;
        }
        File[] allFiles = file.listFiles();
        if (allFiles == null) {
            return null;
        }
        for (int k = 0; k < allFiles.length; k++) {
            final File fi = allFiles[k];
            if (fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".amr") ||
                        suffix.toLowerCase().equals(".mp3") ||
                        suffix.toLowerCase().equals(".wav")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }

    /**
     * 获取文件夹下的所有文件
     * @param path
     * @return
     */
    /*public static List<String> getFileList(String path) {
        List<String> fileList = new ArrayList<>();
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].exists()) {
                fileList.add(files[i].getAbsolutePath());
            }
        }
        return fileList;
    }*/

    /**
     * 获取文件夹下的所有文件
     * @param path
     * @return
     */
    public static List<String> getAllFileList(String path) {
        List<String> fileList = new ArrayList<>();
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        fileList = getAllFiles(fileList, file);
        return fileList;
    }

    private static List<String> getAllFiles(List<String> fileList, File file) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].exists()) {
                if (files[i].isFile()) {
                    fileList.add(files[i].getAbsolutePath());
                } else if (files[i].isDirectory()){
                    fileList = getAllFiles(fileList, files[i]);
                }
            }
        }
        return fileList;
    }
}