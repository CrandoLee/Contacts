package com.example.wechatsample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
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
 * 聊天Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class CallFragment extends Fragment {
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

		List<MyCall> list = new ArrayList<MyCall>();

		// Cursor cursor =
		// getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
		// null, null, null, null);
		// if(cursor.moveToFirst()){
		// do{
		// MyCall call =new MyCall();
		// //号码
		// String number =
		// cursor.getString(cursor.getColumnIndex(Calls.NUMBER));
		// //呼叫类型
		// String type;
		// switch
		// (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE))))
		// {
		// case Calls.INCOMING_TYPE:
		// type = "呼入";
		// break;
		// case Calls.OUTGOING_TYPE:
		// type = "呼出";
		// break;
		// case Calls.MISSED_TYPE:
		// type = "未接";
		// break;
		// default:
		// type = "挂断";//应该是挂断.根据我手机类型判断出的
		// break;
		// }
		// SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new
		// Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE))));
		// //呼叫时间
		// String time = sfd.format(date);
		// //联系人
		// String name =
		// cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));
		// //通话时间,单位:s
		// String duration =
		// cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));
		// Log.d("tag", name);
		// call.setName(name);
		// call.setCallType(type);
		// call.setNumber(number);
		// call.setPosition("未知");
		// call.setDuration(duration);
		// list.add(call);
		// }while(cursor.moveToNext());
		//
		// }

		// URI地址通话记录
		Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
		ContentResolver cr = getActivity().getContentResolver();
		// 集合需要获取类型的集合
		String[] projection = { 
				CallLog.Calls.DATE, // 日期
				CallLog.Calls.NUMBER, // 号码
				CallLog.Calls.TYPE, // 类型
				CallLog.Calls.CACHED_NAME, // 名字
				CallLog.Calls._ID, // id
				CallLog.Calls.DURATION // 时常
		};
		// 查询
		final Cursor cursor = cr.query(uri, projection, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);

		for (int i = 0; i < cursor.getCount(); i++) {
			// 光标移动
			cursor.moveToPosition(i);
			MyCall call = new MyCall();

			Date date;
			String time = "";
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
			date = new Date(Long.parseLong(cursor.getString(0)));
			time = sfd.format(date);
			Log.d("tag", "time " + time);
			String number = cursor.getString(1);
			int tp = cursor.getInt(2);
			String type;
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
				type = "挂断";// 应该是挂断.根据我手机类型判断出的
				break;
			}

			String name = cursor.getString(3);
			int id = cursor.getInt(4);
			String duration = cursor.getString(5);
			
			call.setName(name);
			call.setCallType(type);
			call.setNumber(number);
			call.setTime(time);
			call.setPosition("未知");
			call.setDuration(duration);
			list.add(call);
		}
		// 关闭游标
		cursor.close();

		CallAdapter adapter = new CallAdapter(getActivity(),
				R.layout.callslist, list);

		ListView listView = new ListView(getActivity());
		listView.setAdapter(adapter);

		// TextView v = new TextView(getActivity());
		// params.setMargins(margin, margin, margin, margin);
		// v.setLayoutParams(params);
		// v.setLayoutParams(params);
		// v.setGravity(Gravity.CENTER);
		// v.setText("通话");
		// v.setTextSize((int)
		// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));

		fl.addView(listView);
		return fl;
	}
}
