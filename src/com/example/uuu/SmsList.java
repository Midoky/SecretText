/*王戌琦   2016年4月2日16:04:57
 * 功能：短信列表相关操作
 * 详细介绍：导入适配器和短信信息变量实现菜单可视化。
 * ****************
 * ******已弃用*****
 * ****************
 */
/*
package com.example.uuu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SmsList extends Activity{

	private MyDatabaseHelper dbHelper;
private List<SmsInfo> smslist = new ArrayList<SmsInfo>();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.smslist);
		initSmslists();//初始化列表
		SmsAdapter adapter = new SmsAdapter(SmsList.this,R.layout.item_sms,smslist);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				SmsInfo smsinfo = smslist.get(position);
				Toast.makeText(SmsList.this, smsinfo.getSmsbody(),Toast.LENGTH_LONG).show();//文本显示内容，后期用startActivity替换.
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				//int _id = Integer.valueOf(smsinfo.getID()).intValue();
				// db.execSQL("update TEXT set readflag = ? where id = ?",new String[] {"1", });
				 values.put("readflag","1");//key为字段名，value为值
				  db.update("TEXT", values, "id=?", new String[]{smsinfo.getID()});
				db.close();
				
			}
		});
	}
	private void initSmslists(){
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("TEXT", null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				//遍历cursor对象
				String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
				if(readflag.equals("0")){	//未读短信
					String body = cursor.getString(cursor.getColumnIndex("content"));
					String name = cursor.getString(cursor.getColumnIndex("owner"));
					String time = cursor.getString(cursor.getColumnIndex("time"));
					String __id = cursor.getString(cursor.getColumnIndex("id"));
					String flag = cursor.getString(cursor.getColumnIndex("readflag"));
					SmsInfo msg = new SmsInfo(name,body,time,__id,flag);		//加入豪华午餐
					smslist.add(msg);
				}
				
			}while(cursor.moveToNext());
		}
			
			if(cursor.moveToFirst()){
				do{
					//遍历cursor对象
					String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
					if(readflag.equals("1")){ //已读短信
						String body = cursor.getString(cursor.getColumnIndex("content"));
						String name = cursor.getString(cursor.getColumnIndex("owner"));
						String time = cursor.getString(cursor.getColumnIndex("time"));
						String __id = cursor.getString(cursor.getColumnIndex("id"));
						String flag = cursor.getString(cursor.getColumnIndex("readflag"));
						SmsInfo msg = new SmsInfo(name,body,time,__id,flag);		//加入豪华午餐
						smslist.add(msg);
					}
				}while(cursor.moveToNext());
			}
			if(cursor!=null){
				cursor.close();
			}
	}
		
}
*/