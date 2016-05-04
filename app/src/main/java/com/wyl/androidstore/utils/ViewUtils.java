package com.wyl.androidstore.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 将view从父控件中移除
 *
 * Created by Leon Wu on 2016/5/411:04.
 * Email: yuanliang.wu@weimob.com
 */
public class ViewUtils {
    public static void removeSelfFromParent(View v) {
        if (v != null) {
            ViewParent parent = v.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(v);
            }
        }
    }
}
