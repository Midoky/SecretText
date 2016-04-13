/*������   2016��4��2��16:04:57
 * ���ܣ������б���ز���
 * ��ϸ���ܣ������������Ͷ�����Ϣ����ʵ�ֲ˵����ӻ���
 * ****************
 * ******������*****
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
		initSmslists();//��ʼ���б�
		SmsAdapter adapter = new SmsAdapter(SmsList.this,R.layout.item_sms,smslist);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				SmsInfo smsinfo = smslist.get(position);
				Toast.makeText(SmsList.this, smsinfo.getSmsbody(),Toast.LENGTH_LONG).show();//�ı���ʾ���ݣ�������startActivity�滻.
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				//int _id = Integer.valueOf(smsinfo.getID()).intValue();
				// db.execSQL("update TEXT set readflag = ? where id = ?",new String[] {"1", });
				 values.put("readflag","1");//keyΪ�ֶ�����valueΪֵ
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
				//����cursor����
				String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
				if(readflag.equals("0")){	//δ������
					String body = cursor.getString(cursor.getColumnIndex("content"));
					String name = cursor.getString(cursor.getColumnIndex("owner"));
					String time = cursor.getString(cursor.getColumnIndex("time"));
					String __id = cursor.getString(cursor.getColumnIndex("id"));
					String flag = cursor.getString(cursor.getColumnIndex("readflag"));
					SmsInfo msg = new SmsInfo(name,body,time,__id,flag);		//����������
					smslist.add(msg);
				}
				
			}while(cursor.moveToNext());
		}
			
			if(cursor.moveToFirst()){
				do{
					//����cursor����
					String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
					if(readflag.equals("1")){ //�Ѷ�����
						String body = cursor.getString(cursor.getColumnIndex("content"));
						String name = cursor.getString(cursor.getColumnIndex("owner"));
						String time = cursor.getString(cursor.getColumnIndex("time"));
						String __id = cursor.getString(cursor.getColumnIndex("id"));
						String flag = cursor.getString(cursor.getColumnIndex("readflag"));
						SmsInfo msg = new SmsInfo(name,body,time,__id,flag);		//����������
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