/*������  ʷ���� 
 * 2016��4��7��22:12:27
 * ���ܣ���ʾĳ����ϵ��������������
 * ��ϸ���ܣ����ŶԻ�������������ʽ�����Ͷ��ţ�
 * 
 */
package com.example.uuu;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MsgList extends Activity{
	
	private MyDatabaseHelper dbHelper;
	
	private ListView msgListView;
	
	private EditText inputText;
	
	private Button send;
	
	private MsgAdapter adapter;
	
	private IntentFilter sendFilter;
	
	private SendStatusReceiver sendStatusReceiver;
	
	private List<Msg> msgList = new ArrayList<Msg>();
	
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm"); 
	
	 Date curDate;
	 
	 String str; 
	 String content;
		@Override
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.smslist);
			
			//ʹ��TelephoneManager ��ȡ�豸IME ����������

			TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		   
			String deviceid = tm.getDeviceId(); //�豸IME��Ψһ���к�
		   
			String tel = tm.getLine1Number();//�ֻ�����
			
			initMsgs();
			adapter = new MsgAdapter(MsgList.this,R.layout.msg_item,msgList);
			TextView titleText = (TextView)findViewById(R.id.sms_title_text);
			titleText.setText(getIntent().getStringExtra("name"));
			sendFilter = new IntentFilter();
	        sendFilter.addAction("SENT_SMS_ACTION");
	        sendStatusReceiver = new SendStatusReceiver();
	        registerReceiver(sendStatusReceiver,sendFilter);
			inputText = (EditText) findViewById(R.id.input_text);
			send = (Button)findViewById(R.id.sms_send);
			msgListView = (ListView)findViewById(R.id.sms_list);
			msgListView.setAdapter(adapter);
			msgListView.setSelection(msgList.size());//��ListView��λ�����һ��
			send.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					content =  inputText.getText().toString();
					if(!"".equals(content)){//�����������ݲ�Ϊ��
						/*
						 * if���ټ�һ��&&�ж�
						 * �Ѵ�����뵽����������onClick()����֮ǰ
						 * 
						 */
						
						
						SmsManager smsManager = SmsManager.getDefault();
						Intent sentIntent = new Intent("SENT_SMS_ACTION");
						PendingIntent pi = PendingIntent.getBroadcast(MsgList.this, 0, sentIntent, 0);
						StringBuilder build1 = new StringBuilder();
						build1.append("INTERCEPT");
					    build1.append(content);
					    for(int i = 9; i < build1.length(); i++)//�Զ������ݽ��м���  �������һλ  ���������ݴӵھ�λ��ʼ
					    {
							char x = build1.charAt(i);
							x += 1;
							build1.setCharAt(i, x);
						}
					    final String sendThings = build1.toString();
						smsManager.sendTextMessage(getIntent().getStringExtra("address").toString(), null,sendThings, pi, null);
						//if()
						/*
						 * �������ʧ�ܵĻ���toast��ʾ����
						 */
						
						
					}
				}
			});
		}
		private void initMsgs(){
			dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.query("TEXT", null,null,null,null,null,null);
			if(cursor.moveToFirst()){
				do{
					//����cursor����
					String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
					if(phonenumber.equals(getIntent().getStringExtra("address").toString())){	//��ѯ�Ƿ���ƥ�����
						if(cursor.getString(cursor.getColumnIndex("type")).toString().equals("0")){//����У��򿴿��Ƿ��Ͷ��Ż��ǽ��ն���
						String content = cursor.getString(cursor.getColumnIndex("content"));
						String time = cursor.getString(cursor.getColumnIndex("time"));
						int type = Msg.TYPE_RECEIVED;
						Msg msg = new Msg(content,type,time);		//����������
						msgList.add(msg);
						}else{
							String content = cursor.getString(cursor.getColumnIndex("content"));
							String time = cursor.getString(cursor.getColumnIndex("time"));
							int type = Msg.TYPE_SENT;
							Msg msg = new Msg(content,type,time);
							msgList.add(msg);
						}
					}
					
				}while(cursor.moveToNext());
			}
				if(cursor!=null){
					cursor.close();
				}
		}
		
		@Override
	    protected void onDestroy()
	    {
	    	super.onDestroy();
	    	unregisterReceiver(sendStatusReceiver);
	    }
		 class SendStatusReceiver extends BroadcastReceiver{

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					if(getResultCode()==RESULT_OK){
						curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ�� 
  					  	str = formatter.format(curDate);
						Msg msg = new Msg(content ,Msg.TYPE_SENT,str);
						  Context context1 = getApplicationContext();
    					 //���ݿ�¼����������жϷ��ͳɹ�û�У����ɹ���¼�����ݿ⡣
						  dbHelper = new MyDatabaseHelper(context1,"SMSsecret.db",null,1);
    					  SQLiteDatabase db = dbHelper.getWritableDatabase();
    					  ContentValues values = new ContentValues();
    					  values.put("phonenumber",getIntent().getStringExtra("address").toString());
    					  values.put("owner",getIntent().getStringExtra("name").toString());
    					  values.put("content",content);
    					  values.put("readflag","2");
    					  values.put("time", str);
    					  values.put("type",1);
    					  db.insert("TEXT", null, values);
    					  db.close();   
    					  //�������ݶ������ݿ����
						msgList.add(msg);//��ӵ�ListView
						adapter.notifyDataSetChanged();//��������Ϣʱ��ˢ��ListView�����ʾ
						msgListView.setSelection(msgList.size());//��ListView��λ�����һ��
						inputText.setText("");//���������е�����
					}
					else{
						Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
					}
				}
				 
			 }
	}
