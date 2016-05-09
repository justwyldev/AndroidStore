package com.wyl.androidstore.protocal;

import android.os.SystemClock;
import android.text.TextUtils;

import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.FileUtils;
import com.wyl.androidstore.utils.IOUtils;
import com.wyl.androidstore.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Leon Wu on 2016/5/411:11.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class BaseProtocol<Data> {
    public Data load(int index) {
        SystemClock.sleep(1000);
        String json = loadLocal(index);
        if (TextUtils.isEmpty(json)) {
            json = loadNet(index);
            if (json == null) {
                return null;
            } else {
                saveToLocal(json, index);
            }
            // 保存到本地
            // saveLocal()
        }
        if (!TextUtils.isEmpty(json)) {
            return paserJson(json);
        }
        return null;
    }

    protected abstract Data paserJson(String json);

    /**
     * 保存到本地
     */
    private void saveToLocal(String json, int index) {
        // 找到缓存目录    优先存到sd卡 其次存到cache目录
        String path = FileUtils.getCacheDir();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path + getKey() + "_" + index + getParames()));
            // 写一个时间  判断如果时间过期了重新加载  一般情况下 服务器做的
            long currentTimeMillis = System.currentTimeMillis() + 1000 * 60;
            writer.write(currentTimeMillis + "\r\n");
            writer.write(json.toCharArray());
            writer.flush();
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 需要增加的额外参数
     */
    protected String getParames() {
        return "";
    }

    /**
     * 网络解析
     */
    private String loadNet(int index) {
        String result = null;
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParames());
        if (httpResult != null) {
            result = httpResult.getString();
            httpResult.close();// 记得关流
        }
        return result;
    }

    /**
     * 本地解析
     */
    private String loadLocal(int index) {
        String path = FileUtils.getCacheDir(); // 获取缓存目录
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path + getKey() + "_" + index));
            String readLine = reader.readLine();
            long time = Long.parseLong(readLine);
            if (time > System.currentTimeMillis()) {
                // 证明没有过期
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                return sb.toString();
            }
            // 写一个时间  判断如果时间过期了重新加载  一般情况下 服务器做的
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            IOUtils.close(reader);
        }
        return null;
    }

    protected abstract String getKey();
}