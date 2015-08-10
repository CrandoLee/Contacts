package com.example.contacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.wechatsample.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

/**
 * 聊天Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class CallFragment extends Fragment {
	private ListView listView;
	private List<MyCall> list = new ArrayList<MyCall>();
	private CallAdapter adapter;
	private Handler messageHandler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 8, dm);

		listView = new ListView(getActivity());
		adapter = new CallAdapter(getActivity(), R.layout.callslist, list);
		listView.setAdapter(adapter);
		
//        listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				MyCall call = list.get(position);
//				Intent intent = new Intent(Intent.ACTION_CALL);
//				String callUri = "tel:" + call.getNumber();
//				Toast.makeText(getActivity(),callUri, Toast.LENGTH_SHORT).show();
//				Log.d("tag","uri:" + callUri);
//				intent.setData(Uri.parse(callUri));
//				getActivity().startActivity(intent);
//			}
//		});
		
		fl.addView(listView);
		
		//新开一个线程读取聊天记录信息
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
		List<MyCall> listTemp = new ArrayList<MyCall>();
		
		// URI地址通话记录
		Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
		ContentResolver cr = getActivity().getContentResolver();
		//需要获取类型的集合
		String[] projection = { CallLog.Calls.DATE, 		//日期
				CallLog.Calls.NUMBER, 						//号码
				CallLog.Calls.TYPE, 						//类型
				CallLog.Calls.CACHED_NAME, 					//名字
				CallLog.Calls._ID, 							//id
				CallLog.Calls.DURATION 						//时常
		};
		MyCall call = null;
		Date date;
		String time = "";
		SimpleDateFormat sfd = new SimpleDateFormat("M月d日");
		String number;
		int tp;
		String type;
		String name;
		int id;
		String duration;
		String userNumbers = "";
		
		Cursor cursor = cr.query(uri, projection, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			name = cursor.getString(3);
			number = cursor.getString(1);
			if(TextUtils.isEmpty(name)){
				name = number;
			}	
			
			//这里为了在电话列表中只显示一个名字，当一个人有多个通话记录的时候，在子菜单中做进一步展示
			if(userNumbers.indexOf(number) != -1){
				continue;
			}
			userNumbers += name;
			call = new MyCall();			
			date = new Date(Long.parseLong(cursor.getString(0)));
			time = sfd.format(date);
			tp = cursor.getInt(2);
			switch (tp) {
			case Calls.INCOMING_TYPE:
				type = "呼入";
				break;
			case Calls.OUTGOING_TYPE:
				type = "呼出";
				break;
			case Calls.MISSED_TYPE:
				type = "未接";
				break;
			default:
				type = "挂断";
				break;
			}
			id = cursor.getInt(4);
			duration = cursor.getString(5);
			call.setName(name);
			call.setNumber(number);
			call.setCallType(type);			
			call.setTime(time);
			call.setPosition("未知");
			call.setDuration(duration);
			listTemp.add(call);
		}
		//读取联系人信息结束
		cursor.close();
		
        Message message = Message.obtain();
        message.obj = listTemp;
        messageHandler.sendMessage(message);
	}
	
	//用来更新ListView
    class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
        	list = (List<MyCall>)msg.obj;
        	adapter = new CallAdapter(getActivity(), R.layout.callslist, list);
            listView.setAdapter(adapter);
        }
    }
}
