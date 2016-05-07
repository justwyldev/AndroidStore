package com.wyl.androidstore.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Leon Wu on 2016/5/317:06.
 * Email: yuanliang.wu@weimob.com
 */
public class FileUtils {

    //缓存的文件
    public static final String CACHE_DIR = "cache";
    //保存图片的路径
    public static final String ICON_DIR = "icon";
    //下载文件的路径
    public static final String DOWNLOAD_DIR = "download";
    //根目录
    private static final Object ROOT_DIR = "androidstore";

    public static String getCacheDir() {
        return getDir(CACHE_DIR);
    }

    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    private static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 创建文件夹
     * @param path
     * @return
     */
    private static boolean createDirs(String path) {
        File file=new File(path);
        if(!file.exists()||!file.isDirectory()){
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 获取缓存路径
     * @return
     */
    private static String getCachePath() {
        File f = UIUtils.getContext().getCacheDir();
        return f.getAbsolutePath() + "/";
    }

    /**
     * 返回SD卡的路径
     * @return
     */
    private static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 判断SD卡是否挂载
     * @return
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

}
