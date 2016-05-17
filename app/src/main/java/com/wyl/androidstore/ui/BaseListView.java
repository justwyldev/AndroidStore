package com.wyl.androidstore.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.utils.UIUtils;

/**
 * Created by Leon Wu on 2016/5/516:04.
 * Email: yuanliang.wu@weimob.com
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setSelector(R.drawable.nothing);
        setCacheColorHint(getResources().getColor(R.color.bg_page));
        setDivider(UIUtils.getResources().getDrawable(R.drawable.nothing));
    }

}
