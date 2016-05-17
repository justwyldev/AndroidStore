package com.wyl.androidstore.http.image;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.wyl.androidstore.utils.FileUtils;

/**
 * 初始化BitmapUtils
 *
 * Created by Leon Wu on 2016/5/317:01.
 * Email: yuanliang.wu@weimob.com
 */
public class BitmapHelper {

    public BitmapHelper() {
    }

    private static BitmapUtils bitmapUtils;

    public static BitmapUtils getBitmapUtils(Context context) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context, FileUtils.getIconDir(), 0.3f);
        }
        return bitmapUtils;
    }


}
