package com.example.contacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.wechatsample.R;
import com.example.contacts.ContactsFragment.MessageHandler;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
 * 通讯录Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class MsgFragment extends Fragment {
	private ListView listView;
	private List<MyMsg> list = new ArrayList<MyMsg>();
	private MsgAdapter adapter;
	private Handler messageHandler;
	
	/**
	 * 所有的短信
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * 收件箱短信
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * 已发送短信
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * 草稿箱短信
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
		//读取短信数据
		adapter = new MsgAdapter(getActivity(), R.layout.msglist, list);		
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
		List<MyMsg> listTemp = new ArrayList<MyMsg>();
		Uri uri = Uri.parse(SMS_URI_ALL);
		String[] projection = new String[] { "_id", "address", "person",
	                "body", "date", "type" };
        Cursor cursor = this.getActivity().getContentResolver().query(uri, projection, null, null,
                "date desc");
        Date date;
        String time = "";
        MyMsg myMsg = null;
        SimpleDateFormat sfd = new SimpleDateFormat("M月d日");
        String msgBody;
        String name = "";
        String phoneNum;
        int nameColumn = cursor.getColumnIndex("person");
        int phoneNumberColumn = cursor.getColumnIndex("address");
        int smsbodyColumn = cursor.getColumnIndex("body");
        int dateColumn = cursor.getColumnIndex("date");
        int typeColumn = cursor.getColumnIndex("type");
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
            	myMsg= new MyMsg(); 
            	//name = cursor.getString(nameColumn);
                //Log.d("tag","msg_name:" + name);

    			date = new Date(Long.parseLong(cursor.getString(dateColumn)));
    			time = sfd.format(date);
    			myMsg.setDate(time);
    			phoneNum = cursor.getString(phoneNumberColumn);
                myMsg.setPhoneNumber(phoneNum);
                Log.d("tag","msg_num:" + phoneNum);      
                
        	    Cursor pCur = this.getActivity().getContentResolver().query(  
        	            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] { "display_name"},  
        	            ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",  
        	            new String[] { phoneNum }, null);  
        	    
        	    if (pCur.moveToNext()) {  
        	    	name = pCur.getString(
        	    			pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));  
        	        Log.d("tag","msg_name" + name);
        	        
        	    	pCur.close();
        	        if(TextUtils.isEmpty(name)){
        	        	name = phoneNum;
        	        }
        	    } 
        	    myMsg.setName(name);
                msgBody = cursor.getString(smsbodyColumn);
        		if(msgBody.length() > 40){
        			myMsg.setSmsbody(msgBody.substring(0, 20) + "...");
        		}else{
        			myMsg.setSmsbody(msgBody); 
        		}
                
                myMsg.setType(cursor.getString(typeColumn));
                listTemp.add(myMsg);
            }
            cursor.close();
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
        	list = (List<MyMsg>)msg.obj;
        	Log.i("tag","msg size:" + list.size());
        	adapter = new MsgAdapter(getActivity(), R.layout.msglist, list);
            listView.setAdapter(adapter);
        }
    }
}
