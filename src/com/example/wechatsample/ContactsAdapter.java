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
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		ImageView imageView = (ImageView)view.findViewById(R.id.head_item);
		TextView textView = (TextView)view.findViewById(R.id.contacts_item);
		imageView.setImageResource(myContact.getImageId());
		textView.setText(myContact.getName());
		return view;
	}

	
}
