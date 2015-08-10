package com.example.contacts;

import java.util.List;

import com.example.contacts.CallAdapter.ViewHolder;
import com.example.wechatsample.R;




import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<MyContact>{

	private int resourceId;
	
	public ContactsAdapter(Context context,
			int textViewResourceId, List<MyContact> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyContact myContact = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView)view.findViewById(R.id.head_item);
			viewHolder.textView = (TextView)view.findViewById(R.id.contacts_item);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}

		viewHolder.imageView.setImageResource(myContact.getImageId());
		viewHolder.textView.setText(myContact.getName());
		return view;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
	
}
