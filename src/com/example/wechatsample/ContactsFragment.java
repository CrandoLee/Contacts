package com.example.wechatsample;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * 发现Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class ContactsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
		
		//读取联系人数据
		List<MyContact> list= new ArrayList<MyContact>();

		Cursor cursor;
		 Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
         // 这里是获取联系人表的电话里的信息 
        // 然后在根据"sort-key"排序
        cursor = this.getActivity().getContentResolver().query(
                uri,
                new String[] { "display_name"}, null, null, "sort_key");
        if (cursor.moveToFirst()) {
            do {     
            	MyContact myContact = new MyContact("", R.drawable.contact_head);
            	myContact.setName(cursor.getString(0));              
                if (myContact.getName() != ""){
                	list.add(myContact);
                }             
            } while (cursor.moveToNext());
        }

		ContactsAdapter adapter = new ContactsAdapter(getActivity(), R.layout.contactslist, list);
		
		ListView listView = new ListView(getActivity());
		listView.setAdapter(adapter);
		
		
		
		
		
//		TextView v = new TextView(getActivity());
//		params.setMargins(margin, margin, margin, margin);
//		v.setLayoutParams(params);
//		v.setLayoutParams(params);
//		v.setGravity(Gravity.CENTER);
//		v.setText("联系人");
//		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
//		fl.addView(v);
		
		fl.addView(listView);
		
		return fl;
	}
}
