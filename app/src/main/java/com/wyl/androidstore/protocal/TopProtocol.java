package com.wyl.androidstore.protocal;

import com.wyl.androidstore.utils.LogUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/516:43.
 * Email: yuanliang.wu@weimob.com
 */
public class TopProtocol extends BaseProtocol<List<String>> {

    @Override
    protected String getKey() {
        return "hot";
    }

    @Override
    protected List<String> paserJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < array.length(); i++) {
                String str = array.optString(i);
                list.add(str);
            }
            return list;
        } catch (Exception e) {
            LogUtils.e(e);
            return null;
        }
    }


}