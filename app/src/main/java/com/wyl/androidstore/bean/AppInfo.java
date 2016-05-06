package com.wyl.androidstore.bean;

import java.util.List;
/**
 * app信息
 * Created by Leon Wu on 2016/5/515:19.
 * Email: yuanliang.wu@weimob.com
 */
public class AppInfo {
    private long id; //id
    private String name; //名字
    private String packageName;// 包名
    private String iconUrl; //图片下载地址
    private float stars;  // 评分
    private long size;  //大小
    private String downloadUrl;
    private String downloadNum;//app的下载数量
    private String version;//app的版本
    private String date;//app的发布日期
    private String des;
    private String author;
    private List<String> screen;//截图下载地址
    private List<String> safeUrl;//安全信息图片地址
    private List<String> safeDesUrl;// 安全信息钩钩图片地址
    private List<String> safeDes;//安全信息图片勾勾后面描述信息
    private List<Integer> safeDesColor;//安全信息的文字颜色


    public List<String> getSafeUrl() {
        return safeUrl;
    }

    public void setSafeUrl(List<String> safeUrl) {
        this.safeUrl = safeUrl;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public List<String> getSafeDesUrl() {
        return safeDesUrl;
    }

    public void setSafeDesUrl(List<String> safeDesUrl) {
        this.safeDesUrl = safeDesUrl;
    }

    public List<String> getSafeDes() {
        return safeDes;
    }

    public void setSafeDes(List<String> safeDes) {
        this.safeDes = safeDes;
    }

    public List<Integer> getSafeDesColor() {
        return safeDesColor;
    }

    public void setSafeDesColor(List<Integer> safeDesColor) {
        this.safeDesColor = safeDesColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
