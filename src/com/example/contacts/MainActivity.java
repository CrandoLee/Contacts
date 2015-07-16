package com.example.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.contact.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		List<String> list= new ArrayList<String>();

		Cursor cursor;
		 Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
         // 这里是获取联系人表的电话里的信息 
        // 然后在根据"sort-key"排序
        cursor = getContentResolver().query(
                uri,
                new String[] { "display_name"}, null, null, "sort_key");
        if (cursor.moveToFirst()) {
            do {              
                String name = cursor.getString(0);              
                if (name != null){
                	list.add(name);
                }             
            } while (cursor.moveToNext());
        }

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}
}
