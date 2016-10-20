package com.example.baidumaptest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class friendlist  extends Activity {
	
	private Button button_fa;
	
	private List<Friend> friendList=new ArrayList<Friend>();
	
	private FriendAdapter adapter;
	
	private ListView fdListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friendlist);
		initFriend();
		adapter=new FriendAdapter(friendlist.this,R.layout.friend_list_item,friendList);
		fdListView=(ListView) findViewById(R.id.lvw_friends_list);
		fdListView.setAdapter(adapter);
		
		button_fa=(Button) findViewById(R.id.btn_friends_list_add);
		button_fa.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Friend friend=new Friend("Friends");
				friendList.add(friend);
				adapter.notifyDataSetChanged();
				fdListView.setSelection(friendList.size());
			}
		});
		
		Button buttonrd=(Button)findViewById(R.id.btn_friends_list_radar);
		buttonrd.setOnClickListener(new OnClickListener(){
		
		@Override
		public void onClick(View v){
			Intent intent=new Intent(friendlist.this, MainActivity.class);
			startActivity(intent);
		}
		});
	}
	
	private void initFriend(){
		Friend friend1=new Friend("friends");
		friendList.add(friend1);
	}
}
