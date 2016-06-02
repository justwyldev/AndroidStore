package com.wyl.androidstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public static void startActivity(Context context, Class<?> clz) {
        Intent it = new Intent(context, clz);
        context.startActivity(it);
    }
}
