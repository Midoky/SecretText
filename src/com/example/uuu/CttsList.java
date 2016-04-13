/*������   2016��4��2��16:04:57
 * ���ܣ������б���ز���
 * ��ϸ���ܣ������������Ͷ�����Ϣ����ʵ�ֲ˵����ӻ���
 */

package com.example.uuu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class CttsList extends Activity{

	private MyDatabaseHelper dbHelper;
	
	private CttsAdapter adapter;
	
	private List<Ctts> CttsList = new ArrayList<Ctts>();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cttslist);
		initCttsLists();//��ʼ���б�
		adapter = new CttsAdapter(CttsList.this,R.layout.item_ctts,CttsList);
		ListView listView = (ListView) findViewById(R.id.ctts_list);
		TextView titleText = (TextView)findViewById(R.id.title_text);
		titleText.setText("��ϵ���б�");
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				Ctts Ctts = CttsList.get(position);
				Intent intent = new Intent(CttsList.this,MsgList.class);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("readflag","1");//keyΪ�ֶ�����valueΪֵ
				db.update("CONTACTS", values, "phonenumber=?", new String[]{Ctts.getphoneNumber().toString()});
				db.close();
				adapter.notifyDataSetChanged();//��������Ϣʱ��ˢ��ListVIew�����ʾ
				intent.putExtra("address", Ctts.getphoneNumber().toString());
				intent.putExtra("name", Ctts.getName().toString());
				startActivity(intent);
				finish();
				//Toast.makeText(CttsList.this, Ctts.getName(),Toast.LENGTH_LONG).show();//�ı���ʾ���ݣ�������startActivity�滻.				
			}
		});
	}
	private void initCttsLists(){
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("CONTACTS", null,null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				//����cursor����
				String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
				if(readflag.equals("0")){	//δ������
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
					String flag = cursor.getString(cursor.getColumnIndex("flag"));
					Ctts Ctts = new Ctts(name,phonenumber,flag,readflag);		//����������
					CttsList.add(Ctts);
				}
				
			}while(cursor.moveToNext());
		}
		if(cursor.moveToFirst()){
			do{
				//����cursor����
				String readflag = cursor.getString(cursor.getColumnIndex("readflag"));
				if(readflag.equals("1")){	//δ������
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
					String flag = cursor.getString(cursor.getColumnIndex("flag"));
					Ctts Ctts = new Ctts(name,phonenumber,flag,readflag);		//����������
					CttsList.add(Ctts);
				}
			}while(cursor.moveToNext());
		}
			if(cursor!=null){
				cursor.close();
			}
		
}
			
		}
