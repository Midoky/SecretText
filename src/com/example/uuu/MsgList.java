/*王戌琦  史永飞 
 * 2016年4月7日22:12:27
 * 功能：显示某个联系人所有来往短信
 * 详细介绍：短信对话框，气泡聊天形式；发送短信；
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
			
			//使用TelephoneManager 获取设备IME 及本机号码

			TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		   
			String deviceid = tm.getDeviceId(); //设备IME，唯一序列号
		   
			String tel = tm.getLine1Number();//手机号码
			
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
			msgListView.setSelection(msgList.size());//将ListView定位到最后一行
			send.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					content =  inputText.getText().toString();
					if(!"".equals(content)){//如果输入的内容不为空
						/*
						 * if里再加一个&&判断
						 * 把代码加入到这里面和这个onClick()函数之前
						 * 
						 */
						
						
						SmsManager smsManager = SmsManager.getDefault();
						Intent sentIntent = new Intent("SENT_SMS_ACTION");
						PendingIntent pi = PendingIntent.getBroadcast(MsgList.this, 0, sentIntent, 0);
						StringBuilder build1 = new StringBuilder();
						build1.append("INTERCEPT");
					    build1.append(content);
					    for(int i = 9; i < build1.length(); i++)//对短信内容进行加密  编码后移一位  真正的内容从第九位开始
					    {
							char x = build1.charAt(i);
							x += 1;
							build1.setCharAt(i, x);
						}
					    final String sendThings = build1.toString();
						smsManager.sendTextMessage(getIntent().getStringExtra("address").toString(), null,sendThings, pi, null);
						//if()
						/*
						 * 如果发送失败的话，toast提示出错。
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
					//遍历cursor对象
					String phonenumber = cursor.getString(cursor.getColumnIndex("phonenumber"));
					if(phonenumber.equals(getIntent().getStringExtra("address").toString())){	//查询是否有匹配短信
						if(cursor.getString(cursor.getColumnIndex("type")).toString().equals("0")){//如果有，则看看是发送短信还是接收短信
						String content = cursor.getString(cursor.getColumnIndex("content"));
						String time = cursor.getString(cursor.getColumnIndex("time"));
						int type = Msg.TYPE_RECEIVED;
						Msg msg = new Msg(content,type,time);		//加入豪华午餐
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
						curDate = new Date(System.currentTimeMillis());//获取当前时间 
  					  	str = formatter.format(curDate);
						Msg msg = new Msg(content ,Msg.TYPE_SENT,str);
						  Context context1 = getApplicationContext();
    					 //数据库录入操作，先判断发送成功没有，不成功不录入数据库。
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
    					  //以上内容都是数据库操作
						msgList.add(msg);//添加到ListView
						adapter.notifyDataSetChanged();//当有新消息时，刷新ListView里的显示
						msgListView.setSelection(msgList.size());//将ListView定位到最后一行
						inputText.setText("");//清空输入框中的内容
					}
					else{
						Toast.makeText(context, "发送失败", Toast.LENGTH_LONG).show();
					}
				}
				 
			 }
	}
