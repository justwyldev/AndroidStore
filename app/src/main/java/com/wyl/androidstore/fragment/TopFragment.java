package com.wyl.androidstore.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wyl.androidstore.R;
import com.wyl.androidstore.protocal.TopProtocol;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.widgets.FlowLayout;
import com.wyl.androidstore.utils.DrawableUtils;
import com.wyl.androidstore.utils.UIUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Leon Wu on 2016/5/511:16.
 * Email: yuanliang.wu@weimob.com
 */
public class TopFragment extends BaseFragment {
    private List<String> data;
    private FlowLayout layout;
    @Override
    public View createLoadedView() {
        // 防止在小屏的手机上显示不全,需要通过ScrollView 包裹界面
        ScrollView mScrollView=new ScrollView(UIUtils.getContext());
        mScrollView.setFillViewport(true);//
        layout=new FlowLayout(getActivity());
        layout.setBackgroundResource(R.drawable.grid_item_bg_normal);
        int layoutPadding=UIUtils.dip2px(13);
        layout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
        layout.setHorizontalSpacing(layoutPadding);
        layout.setVerticalSpacing(layoutPadding);

        int textPaddingV = UIUtils.dip2px(4);
        int textPaddingH = UIUtils.dip2px(7);
        int backColor = 0xffcecece;
        int radius = UIUtils.dip2px(5);

        // 代码动态创建图片
        GradientDrawable pressDrawable=DrawableUtils.createDrawable(backColor, backColor, radius);
        Random mRdm=new Random();
        for(int i=0;i<data.size();i++){
            TextView tv = new TextView(UIUtils.getContext());
            // 随机颜色
            int red = 32 + mRdm.nextInt(208);
            int green = 32 + mRdm.nextInt(208);
            int blue = 32 + mRdm.nextInt(208);
            int color = Color.rgb(red, green, blue);
            //设置圆角
            GradientDrawable normalDrawable = DrawableUtils.createDrawable(color, color, radius);
            StateListDrawable selector = DrawableUtils.createSelector(normalDrawable, pressDrawable);
            tv.setBackgroundDrawable(selector);
            final String text=data.get(i);
            tv.setText(text);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                }
            });
            layout.addView(tv);
        }

        mScrollView.addView(layout);
        return mScrollView;
    }

    @Override
    public LoadingPage.LoadResult load() {
        TopProtocol protocol=new TopProtocol();
        data = protocol.load(0);

        return  check(data);
    }
}
