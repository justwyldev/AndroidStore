package com.wyl.androidstore.protocal;

import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/516:40.
 * Email: yuanliang.wu@weimob.com
 */
public class AppProtocol extends BaseProtocol<List<AppInfo>>{

    @Override
    protected List<AppInfo> paserJson(String json) {
        try {
            List<AppInfo> list = new ArrayList<AppInfo>();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.setId(obj.getLong("id"));
                info.setName(obj.getString("name"));
                info.setPackageName(obj.getString("packageName"));
                info.setIconUrl(obj.getString("iconUrl"));
                info.setStars(Float.valueOf(obj.getString("stars")));
                info.setSize(obj.getLong("size"));
                info.setDownloadUrl(obj.getString("downloadUrl"));
                info.setDes(obj.getString("des"));
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            LogUtils.e(e);
            return null;
        }
    }

    @Override
    protected String getKey() {
        return "app";
    }

}