package com.example.contacts;

import java.util.List;

import com.example.contacts.CallAdapter.ViewHolder;
import com.example.wechatsample.R;






import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<MyMsg>{

	private int resourceId;
	
	public MsgAdapter(Context context,
			int textViewResourceId, List<MyMsg> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyMsg myMsg = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.msgName = (TextView)view.findViewById(R.id.msg_name);
			viewHolder.msgInfo = (TextView)view.findViewById(R.id.msg_info);
			viewHolder.msgDate = (TextView)view.findViewById(R.id.msg_date);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}

		viewHolder.msgName.setText(myMsg.getName());
		viewHolder.msgInfo.setText(myMsg.getSmsbody());
		viewHolder.msgDate.setText(myMsg.getDate());
		return view;
	}

	class ViewHolder{
		TextView msgName;
		TextView msgInfo;
		TextView msgDate;
	}
	
}
