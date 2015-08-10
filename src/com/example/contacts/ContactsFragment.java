package com.example.contacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.wechatsample.R;
import com.example.contacts.CallFragment.MessageHandler;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.CallLog.Calls;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
	private List<MyContact> list = new ArrayList<MyContact>();
	private ListView listView;
	private ContactsAdapter adapter;
	private MessageHandler messageHandler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
	
		//读取联系人数据
		adapter = new ContactsAdapter(getActivity(), R.layout.contactslist, list);		
		listView = new ListView(getActivity());
		listView.setAdapter(adapter);			
		fl.addView(listView);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateView();
			}
		}).start();
		Looper looper = Looper.myLooper();

        messageHandler = new MessageHandler(looper);
		return fl;
	}
	
	// 更新视图
	public void updateView() {
		List<MyContact> listTemp = new ArrayList<MyContact>();
		Cursor cursor;
		//获取联系人信息
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        cursor = this.getActivity().getContentResolver().query(uri,
                new String[] { "display_name"}, null, null, "sort_key");
        MyContact myContact = null;
        if (cursor.moveToFirst()) {
            do {     
            	myContact = new MyContact("", R.drawable.contact_head);
            	myContact.setName(cursor.getString(0));              
                if (myContact.getName() != ""){
                	listTemp.add(myContact);
                }             
            } while (cursor.moveToNext());
        }

        Message message = Message.obtain();
        message.obj = listTemp;
        messageHandler.sendMessage(message);
	}
	
    class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
        	list = (List<MyContact>)msg.obj;
        	adapter = new ContactsAdapter(getActivity(), R.layout.contactslist, list);
            listView.setAdapter(adapter);
        }
    }
}
