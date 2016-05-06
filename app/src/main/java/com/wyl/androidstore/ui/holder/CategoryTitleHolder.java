package com.wyl.androidstore.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.CategoryInfo;
import com.wyl.androidstore.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv_title;

	@Override
	protected View initView() {
		View view= UIUtils.inflate(R.layout.category_item_title);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	protected void refreshView() {
		tv_title.setText(getData().getTitle());
	}

}
