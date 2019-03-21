package com.coahr.thoughtrui.mvp.view.recorder;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/8
 * 描述：合并录音文件
 */
public class MergeFiles {
    /**
     * 需求:将两个amr格式音频文件合并为1个
     * 注意:amr格式的头文件为6个字节的长度
     *
     * @param partsPaths 各部分路径
     * @param targetPath 合并后路径
     */
    public static void uniteAMRFile(final List<String> partsPaths, final String targetPath, String recorderName,MergeFileListener mergeFileListener) {
        if (partsPaths != null && partsPaths.size() > 0) {
            if (targetPath == null || recorderName==null) {
                mergeFileListener.mergeFailure("目标文件或文件名为空");
                return;
            }
            File targetP = new File(targetPath);
            if (!targetP.exists()) {
                targetP.mkdirs();
            }
            try {
                File outPath = new File(targetP, recorderName);
                if (!outPath.exists()) {
                    outPath.createNewFile();
                }
                //读写操作
                FileOutputStream fos = new FileOutputStream(outPath);
                RandomAccessFile ra = null;
                for (int i = 0; i < partsPaths.size(); i++) {
                    ra = new RandomAccessFile(partsPaths.get(i), "r");
                    if (i != 0) {
                        ra.seek(6);
                    }
                    byte[] buffer = new byte[1024 * 8];
                    int len = 0;
                    while ((len = ra.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                }
                ra.close();
                fos.close();
                mergeFileListener.mergeSuccess(outPath.getAbsolutePath());
            } catch (IOException e) {
                mergeFileListener.mergeFailure(e.toString());
                e.printStackTrace();
            }
        } else {
            mergeFileListener.mergeFailure("列表不能为空");
        }
    }
}
