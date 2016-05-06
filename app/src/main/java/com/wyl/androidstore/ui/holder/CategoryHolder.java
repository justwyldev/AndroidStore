package com.wyl.androidstore.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.CategoryInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.UIUtils;

public class CategoryHolder extends BaseHolder<CategoryInfo> implements OnClickListener{

	private ImageView iv1, iv2, iv3;
	private TextView tv1, tv2, tv3;
	private RelativeLayout rl1, rl2, rl3;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.category_item);
		iv1 = (ImageView) view.findViewById(R.id.iv_1);
		iv2 = (ImageView) view.findViewById(R.id.iv_2);
		iv3 = (ImageView) view.findViewById(R.id.iv_3);

		tv1 = (TextView) view.findViewById(R.id.tv_1);
		tv2 = (TextView) view.findViewById(R.id.tv_2);
		tv3 = (TextView) view.findViewById(R.id.tv_3);

		rl1 = (RelativeLayout) view.findViewById(R.id.rl_1);
		rl2 = (RelativeLayout) view.findViewById(R.id.rl_2);
		rl3 = (RelativeLayout) view.findViewById(R.id.rl_3);
		rl1.setOnClickListener(this);
		rl2.setOnClickListener(this);
		rl3.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView() {
		CategoryInfo data = getData();
		String name1 = data.getName1();
		String name2 = data.getName2();
		String name3 = data.getName3();

		String key1 = data.getImageUrl1();
		String key2 = data.getImageUrl2();
		String key3 = data.getImageUrl3();

		if (TextUtils.isEmpty(key1)) {
			rl1.setEnabled(false);
			tv1.setText("");
			iv1.setImageDrawable(null);
		} else {
			rl1.setEnabled(true);
			tv1.setText(name1);
			iv1.setTag(key1);
			bitmapUtils.display(iv1, HttpHelper.URL+"image?name="+key1);
		}
		if (TextUtils.isEmpty(key2)) {
			rl2.setEnabled(false);
			tv2.setText("");
			iv2.setImageDrawable(null);
		} else {
			rl2.setEnabled(true);
			tv2.setText(name2);
			iv2.setTag(key2);
			bitmapUtils.display(iv2, HttpHelper.URL+"image?name="+key2);
		}
		if (TextUtils.isEmpty(key3)) {
			rl3.setEnabled(false);
			tv3.setText("");
			iv3.setImageDrawable(null);
		} else {
			rl3.setEnabled(true);
			tv3.setText(name3);
			iv3.setTag(key3);
			bitmapUtils.display(iv3, HttpHelper.URL+"image?name="+key3);
		}
	}

	@Override
	public void recycle() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_1:
				if (!TextUtils.isEmpty(tv1.getText().toString())) {
					UIUtils.showToast(tv1.getText().toString());
				}
				break;
			case R.id.rl_2:
				if (!TextUtils.isEmpty(tv2.getText().toString())) {
					UIUtils.showToast(tv2.getText().toString());
				}
				break;
			case R.id.rl_3:
				if (!TextUtils.isEmpty(tv3.getText().toString())) {
					UIUtils.showToast(tv3.getText().toString());
				}
				break;
			default:
				break;
		}
	}

}
