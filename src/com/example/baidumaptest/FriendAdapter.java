package com.example.baidumaptest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<Friend> {

	private int resourceId;

	public FriendAdapter(Context context, int textViewResourceId,List<Friend> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Friend friend = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.friendCONTENT = (TextView) view.findViewById(R.id.name_cell);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.friendCONTENT.setText(friend.getContent());
		return view;
	}

	class ViewHolder {

		TextView friendCONTENT;
	}
}
