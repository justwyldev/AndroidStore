package com.wyl.androidstore.protocal;

import com.wyl.androidstore.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/516:07.
 * Email: yuanliang.wu@weimob.com
 */
public class HomePotocol extends BaseProtocol<List<AppInfo>> {
    private List<String> pictureUrl;

    public List<String> getPictureUrl() {
        return pictureUrl;
    }

    /**
     * 解析json
     */
    public List<AppInfo> paserJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("picture");
            pictureUrl = new ArrayList<String>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    pictureUrl.add(jsonArray.getString(i));
                }
            }


            List<AppInfo> list = new ArrayList<AppInfo>();
            JSONArray array = jsonObject.getJSONArray("list");
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
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String getKey() {
        return "home";
    }

}
