package com.example.hd.cuterecorder;


import android.util.Log;

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
    public static void copyFile(String oldPath, String newPath, String name) {
        if (oldPath == null || newPath == null) {
            return;
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
                if (newfile.exists()) {        //如果目标文件存先删除在复制
                    deleteFile(newfile);
                }
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newfile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

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
     * 复制文件
     *
     * @param src
     * @param destPath
     * @param FileName
     * @return
     */
    public static boolean copyDate(String src, String destPath, String FileName, boolean deleteOld) {
        Log.e("复制录音进件","开始复制");
        boolean result = false;
        if ((src == null) || (destPath == null)) {
            Log.e("复制录音进件","文件路径为空");
            return result;
        }

        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        File haveFile = new File(destPath + FileName);
        if (!haveFile.exists()) {
            deleteFile(haveFile);
        }
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(destPath + FileName).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
        } catch (IOException e) {
            Log.e("复制录音进件",e.toString());
            return result;
        }

       try {
            srcChannel.close();
            dstChannel.close();
            if (deleteOld) {
                boolean b = deleteFile(src);
                if (b) {
                    Log.e("复制录音进件","删除旧文件成功");
                } else {
                    Log.e("复制录音进件","删除旧文件成功");
                }
            }
            result = true;
           Log.e("复制录音进件","复制成功");
       } catch (IOException e) {
           Log.e("复制录音进件",e.toString());
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
    public static void deleteFiles(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static boolean deleteFile(String path) {
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
     * 读取文件夹文件
     *
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
                        suffix.toLowerCase().equals(".png")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }

}
