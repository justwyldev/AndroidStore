package com.wyl.androidstore.protocal;

import com.wyl.androidstore.bean.SubjectInfo;
import com.wyl.androidstore.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/516:42.
 * Email: yuanliang.wu@weimob.com
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

    @Override
    protected List<SubjectInfo> paserJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            List<SubjectInfo> list = new ArrayList<SubjectInfo>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.optJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.setDes(obj.optString("des"));
                info.setUrl(obj.optString("url"));
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
        return "subject";
    }

}
