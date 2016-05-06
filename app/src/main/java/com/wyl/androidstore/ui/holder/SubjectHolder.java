package com.wyl.androidstore.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.SubjectInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.UIUtils;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private ImageView iv;
    private TextView tv;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.subject_item);
        iv = (ImageView) view.findViewById(R.id.item_icon);
        tv = (TextView) view.findViewById(R.id.item_txt);
        return view;
    }

    @Override
    protected void refreshView() {
        SubjectInfo data = getData();
        String des = data.getDes();
        String url = data.getUrl();
        tv.setText(des);
        iv.setTag(url);
        bitmapUtils.display(iv, HttpHelper.URL + "image?name=" + url);
    }

}
