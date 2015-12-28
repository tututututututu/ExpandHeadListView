package com.tutu.gogogo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShadowListViewActivity extends Activity {

	private ShadowListView slv;
	private ArrayList<String> data = new ArrayList<String>();
	private MyAdapter adapter;
	private LinearLayout ll_top;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.shadow_listview);

		for (int i = 0; i < 20; i++) {
			data.add("test data " + i);
		}
		adapter = new MyAdapter();

		slv = (ShadowListView) findViewById(R.id.slv);
		ll_top = (LinearLayout) findViewById(R.id.ll_top);

		slv.setAdapter(adapter);
		slv.setTopView(ll_top);
		slv.setTopViewHeight((int) getResources().getDimension(R.dimen.height));

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = LayoutInflater.from(ShadowListViewActivity.this)
						.inflate(R.layout.text_view_item, null);
				holder.tv = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv.setText(data.get(position));

			return convertView;
		}

	}

	public static class ViewHolder {
		TextView tv;
	}
}
