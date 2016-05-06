package com.wyl.androidstore.ui.holder;


import android.view.View;
import android.widget.ImageView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.UIUtils;

public class AppDetailScreenHolder extends BaseHolder<AppInfo> {
	private ImageView[] mIv;
	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_screen);
		mIv = new ImageView[5];
		mIv[0] = (ImageView) view.findViewById(R.id.screen_1);
		mIv[1] = (ImageView) view.findViewById(R.id.screen_2);
		mIv[2] = (ImageView) view.findViewById(R.id.screen_3);
		mIv[3] = (ImageView) view.findViewById(R.id.screen_4);
		mIv[4] = (ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	protected void refreshView() {
		AppInfo info = getData();
		for (int i = 0; i < 5; i++) {
			if (i < info.getScreen().size()) {
				bitmapUtils.display(mIv[i], HttpHelper.URL + "image?name=" +info.getScreen().get(i));
				mIv[i].setVisibility(View.VISIBLE);
			} else {
				mIv[i].setVisibility(View.GONE);
			}
		}
	}

}
