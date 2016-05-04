package com.wyl.androidstore.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Leon Wu on 2016/5/411:03.
 * Email: yuanliang.wu@weimob.com
 */
public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.e(e);
            }
        }
        return true;
    }
}
