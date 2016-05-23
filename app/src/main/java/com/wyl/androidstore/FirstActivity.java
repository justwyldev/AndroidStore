package com.wyl.androidstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Window;

/**
 * 启动页
 * Created by Leon Wu on 2016/5/2220:59.
 * Email: yuanliang.wu@weimob.com
 */
public class FirstActivity extends BaseActivity {
    public static final int MSG_WHAT_NUM = 200;

    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_WHAT_NUM) {
                startActivity(new Intent(FirstActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        startTimer();
    }

    public void startTimer() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(MSG_WHAT_NUM);
            }
        }.start();
    }
}
