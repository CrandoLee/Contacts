package com.example.wechatsample;

import java.util.List;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallAdapter extends ArrayAdapter<MyCall>{

	private int resourceId;
	
	public CallAdapter(Context context,
			int textViewResourceId, List<MyCall> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyCall myCall = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView callName = (TextView)view.findViewById(R.id.call_name);
		TextView callNumber = (TextView)view.findViewById(R.id.call_number);
		TextView callPosition = (TextView)view.findViewById(R.id.call_position);
		TextView callTime = (TextView)view.findViewById(R.id.call_time);
		
		callName.setText(myCall.getName());
		callNumber.setText(myCall.getNumber());
		callPosition.setText(myCall.getPosition());
		callTime.setText(myCall.getTime());
		
		return view;
	}

	
}
