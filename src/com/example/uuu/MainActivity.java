/*������   2016��4��20��
 * ���ܣ����˵�
 * ��ϸ���ܣ����ֹ��ܰ������ϡ�
 */

package com.example.uuu;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private MyDatabaseHelper dbHelper;
	private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		TextView titleText = (TextView)findViewById(R.id.main_title_text);
		titleText.setText("��˽����");
		Intent intent = new Intent(this, MsgService.class);
		startService(intent);
		//���if�����ж��Ƿ���Ҫ����login
/*		if(true){//if�ﻹ��Ҫ�Ӵ���
			Intent intent2 = new Intent(this,Login.class);
			startActivity(intent2);
			finish();
		}
		*/
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,CttsList.class);//��½��Ҫ�޸�
				startActivity(intent);
			}
		} );
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,Settings.class);
				startActivity(intent);
			}
		} );
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AddContact.class);
				startActivity(intent);
			}
		} );
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
			dbHelper.getWritableDatabase();
			dbHelper.close();
		//	getContacts();
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            if ((System.currentTimeMillis() - mExitTime) > 2000) {

	                Toast.makeText(this,"�ٰ�һ���˳�����" ,Toast.LENGTH_SHORT).show();
	                mExitTime = System.currentTimeMillis();

	            } else {
	                finish();
	            }
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
}
	//��ϵͳ��ϵ���л�ȡ��ϵ��
/*	private void getContacts(){//��ȡϵͳ��ϵ�˴���app���ݿ�
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
		Cursor cursor =null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try{
			//��ѯ��ϵ������
			cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			if(cursor.moveToFirst()){
			do{
				//��ȡ��ϵ������
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				//��ȡ��ϵ�˺���
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				ContentValues values = new ContentValues();
				values.put("name", name);
				values.put("phonenumber",number);
				values.put("flag", "2");//û����Ϣ
				db.insert("CONTACTS", null, values);
				db.close();
			}while(cursor.moveToNext());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
	}
	*/
	}

   