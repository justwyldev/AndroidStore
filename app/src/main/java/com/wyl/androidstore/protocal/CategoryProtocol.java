package com.wyl.androidstore.protocal;

import com.wyl.androidstore.bean.CategoryInfo;
import com.wyl.androidstore.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/516:41.
 * Email: yuanliang.wu@weimob.com
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    protected String getKey() {
        return "category";
    }

    @Override
    protected List<CategoryInfo> paserJson(String json) {
        try {
            List<CategoryInfo> list = new ArrayList<CategoryInfo>();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String title = obj.getString("title");
                CategoryInfo info = new CategoryInfo();
                info.setTitle(true);
                info.setTitle(title);
                list.add(info);

                JSONArray infos = obj.getJSONArray("infos");
                for (int j = 0; j < infos.length(); j++) {
                    JSONObject category = infos.optJSONObject(j);
                    info = new CategoryInfo();
                    info.setImageUrl1(category.optString("url1"));
                    info.setImageUrl2(category.optString("url2"));
                    info.setImageUrl3(category.optString("url3"));
                    info.setName1(category.optString("name1"));
                    info.setName2(category.optString("name2"));
                    info.setName3(category.optString("name3"));
                    info.setTitle(false);
                    info.setTitle(title);
                    list.add(info);
                }
            }
            return list;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return null;
    }
}
