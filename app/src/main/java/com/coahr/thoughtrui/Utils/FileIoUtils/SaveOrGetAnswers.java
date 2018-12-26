package com.coahr.thoughtrui.Utils.FileIoUtils;


import com.coahr.thoughtrui.Utils.ToastUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Leehor
 * on 2018/11/18
 * on 19:12
 * IO操作用于保存答案和说明备注
 */
public class SaveOrGetAnswers {
    //保存文件到sd卡
    public  static  boolean saveToFile(String FilePath,String massage,boolean append) {
        BufferedWriter out = null;
      /*
      输出流的构造参数1：可以是File对象 也可以是文件路径
      输出流的构造参数2：默认为False=>覆盖内容； true=>追加内容
       */
      File filepath=new File(FilePath);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        File file = new File(FilePath, "AnswerAndRemark.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
                out.newLine();
                if (append) {
                    out.write("\r\n");
                }
                out.write(massage);
                ToastUtils.showLong("保存成功");
            } catch (IOException e1) {
                e1.printStackTrace();
                ToastUtils.showLong(e1.toString());
            }finally {
                if (out != null) {
                    try {
                        out.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtils.showLong(e.toString());
                        return false;
                    }
                }
            }
        return false;
    }


    /**
     * 使用FileWriter进行文本内容的追加
     * @param file
     * @param content
     */
    private static boolean addTxtToFileWrite(File file, String content,boolean append){
        FileWriter writer = null;
        try {
            //FileWriter(file, true),第二个参数为true是追加内容，false是覆盖
            writer = new FileWriter(file, append);
            writer.write("\r\n");//换行
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(writer != null){
                    writer.close();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    //从SD卡读取文件
    public static String readFromFile(String FilePath) {
        //读的时候要用字符流  万一里面有中文
        BufferedReader reader = null;
        FileInputStream fis;
        StringBuilder sbd = new StringBuilder();
        File file=new File(FilePath);
        if (!file.exists()){
            return "";
        }

        try {
            fis = new FileInputStream(FilePath + "/AnswerAndRemark.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String row;
            while ((row = reader.readLine()) != null) {
                sbd.append(row);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbd.toString();
    }

    /**
     * 获取字符串
     * @param path
     * @param flag
     * @return
     */
    public static String getString(String path,String flag){
        int i = path.lastIndexOf(flag);
        String substring_name = path.substring(i+1);
        return substring_name;
    }
}
