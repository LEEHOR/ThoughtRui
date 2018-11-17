package com.coahr.thoughtrui.Utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.baidu.location.g.a.i;

/**
 * 存储空间管理
 * 
 * @author
 * 
 */
public class StoreSpaceUtils {
	
	/**
	 * 获取默认存储路径
	 * @return
	 */
	public static String getDefaultPath(){
		if(isExistSDCard()){
			return getSDCardPath();
		}else{
			return Environment.getExternalStorageDirectory().getPath();
		}
	}
	

	/**
	 * 计算总空间
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static long getTotalSize(String path) {
		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getBlockCount() * fileStats.getBlockSize();
	}
	
	/**
	 * 计算剩余空间
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getAvailableSize(String path) {
		if (path == null) {
			return 0;
		}

		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
	}

	/**
	 * 获取系统可读写的总空间
	 * 
	 * @return
	 */
	public static long getSysTotalSize() {
		return getTotalSize("/data");
	}

	/**
	 * 计算系统的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getSystemAvailableSize() {
		return getAvailableSize("/data");
	}
	
	/**
	 * 判断sd卡是否存在
	 * @return
	 */
	public static boolean isExistSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	}

	/**
	 * 获取sd卡的路径
	 * @return
	 */
	public static String getSDCardPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		
		if(sdDir == null){
			return null;
		}
		
		return sdDir.toString();
	}
	
	/**
	 * 获取SD卡的总空间
	 * 
	 * @return
	 */
	public static long getSDTotalSize() {
		if (isExistSDCard()) {
			return getTotalSize(getSDCardPath());
		}

		return 0;
	}
	

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getSDAvailableSize() {
		if (isExistSDCard()) {
			return getAvailableSize(getSDCardPath());
		}

		return 0;
	}

	public static List<String> getPictures(final String strPath) {
		List<String> list = new ArrayList<String>();
		File file = new File(strPath);
		if (!file.exists()){
			return null;
		}
		File[] allfiles = file.listFiles();
		if (allfiles == null) {
			return null;
		}
		for (int k = 0; k < allfiles.length; k++) {
			final File fi = allfiles[i];
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

	/**
	 * 复制单个文件
	 *
	 * @param oldPath String 原文件路径 如：c:/fqf.txt
	 * @param newPath String 复制后路径 如：f:/fqf.txt
	 * @Param name      String   复制后名字
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath,String name) {
		try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (!newfile.exists()) {
                newfile.mkdir();
            }
            File haveFile = new File(newPath + name);
            if (!haveFile.exists()) {
                if (oldfile.exists()) { //文件存在时
                    InputStream inStream = new FileInputStream(oldPath); //读入原文件
                    FileOutputStream fs = new FileOutputStream(newPath + name);
                    byte[] buffer = new byte[1444];
                    int length;
                    while ((byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        System.out.println(bytesum);
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                } else {

                }
            } else {
                System.out.println("当前文件已存在");
            }
            } catch(Exception e){
                System.out.println("复制单个文件操作出错");
                e.printStackTrace();

            }


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
	 * @param src 源文件
	 * @param destPath 目标文件路径
	 * @return boolean 成功true、失败false
	 */
	public static boolean copyFile(File src, String destPath,String FileName) {
		boolean result = false;
		if ((src == null) || (destPath== null)) {
			return result;
		}
		File dest= new File(destPath);
		if (!dest.exists()) {
			dest.mkdir();
		}
		File haveFile=new File(destPath+FileName);
		if (!haveFile.exists()){
			FileChannel srcChannel = null;
			FileChannel dstChannel = null;
			try {
				srcChannel = new FileInputStream(src).getChannel();
				dstChannel = new FileOutputStream(destPath+FileName).getChannel();
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
	 * 得到后缀或文件名
	 * @param path
	 * @param flag
	 * @return
	 */
	public static String getE(String path,String flag){
		int i = path.lastIndexOf(flag);
		String substring_name = path.substring(i+1);
		return substring_name;
	}
}
