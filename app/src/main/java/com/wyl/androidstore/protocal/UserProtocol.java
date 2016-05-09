package com.wyl.androidstore.protocal;

import com.wyl.androidstore.bean.UserInfo;
import com.wyl.androidstore.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/411:10.
 * Email: yuanliang.wu@weimob.com
 */
public class UserProtocol extends BaseProtocol<List<UserInfo>> {

    @Override
    protected String getKey() {
        return "user";
    }

    @Override
    protected List<UserInfo> paserJson(String json) {
        try {
            List<UserInfo> list = new ArrayList<UserInfo>();
            JSONObject obj = new JSONObject(json);
            UserInfo info = new UserInfo();
            info.setName(obj.getString("name"));
            info.setEmail(obj.getString("email"));
            info.setUrl(obj.getString("url"));
            list.add(info);
            return list;
        } catch (Exception e) {
            LogUtils.e(e);
            return null;
        }
    }
}
