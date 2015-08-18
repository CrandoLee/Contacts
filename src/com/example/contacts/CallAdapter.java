package com.example.contacts;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.wechatsample.R;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		MyCall myCall = getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.callName = (TextView)view.findViewById(R.id.call_name);
			viewHolder.callNumber = (TextView)view.findViewById(R.id.call_number);
			viewHolder.callPosition = (TextView)view.findViewById(R.id.call_position);
			viewHolder.callTime = (TextView)view.findViewById(R.id.call_time);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}	
		viewHolder.callName.setText(myCall.getName());
		
		//检查是否没有名字的电话，如果是则名字显示成为电话号码
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(myCall.getName());
		if(isNum.matches()){
			viewHolder.callNumber.setVisibility(View.GONE);
		}else{
			viewHolder.callNumber.setText(myCall.getNumber());
		}
		
		viewHolder.callPosition.setText(myCall.getPosition());
		viewHolder.callTime.setText(myCall.getTime());
		
		return view;
	}

	class ViewHolder{
		TextView callName;
		TextView callNumber;
		TextView callPosition;
		TextView callTime;
	}
}
