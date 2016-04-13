/* 拦截和处理：史永飞   
 * 服务和数据库：王戌琦
 * 通知：仝乐
 * 时间：2016年4月5日13:57:10
 * 功能：拦截短信，解密，入库，通知提示
 * 详细介绍：代码注释
 */

package com.example.uuu;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MsgService extends Service{
	private boolean D=true;
	private ReceiveMessage mReceiver;
	private Notification.Builder builder;
	private NotificationManager manager;

	 @Override
	 public IBinder onBind(Intent intent) {
		    // TODO Auto-generated method stub
		    return null;
		  }
	 @Override
	  public void onCreate() {
	    if(D){
	      Log.i("msgservice"," onCreate()");
	    }
	    //注册动态广播
	    mReceiver=new ReceiveMessage();
	    IntentFilter filter=new IntentFilter();
	    filter.setPriority(Integer.MAX_VALUE);
	    filter.addAction("android.provider.Telephony.SMS_RECEIVED");
	    registerReceiver(mReceiver, filter);
	    super.onCreate();
	  }
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    if(D){
	      Log.i("msgservice","onStartCommand");
	    }
	    return super.onStartCommand(intent, flags, startId);
	  }
	  @Override
	  public void onDestroy() {
	    if(D){
	      Log.i("msgservice","onDestroy() ");
	    }
	    //取消广播接收者
	    unregisterReceiver(mReceiver);
	    mReceiver=null;
	    super.onDestroy();
	  }
	  class ReceiveMessage extends BroadcastReceiver {
		 private String address;
		 private String fullMessage;
		 private MyDatabaseHelper dbHelper;
		 private String flag;
		 
		 SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm"); 
		 Date curDate;
		 String str; 
	  // 当接收到短息时被触发
	  @SuppressLint("NewApi")
	@Override
	  public void onReceive(Context context, Intent intent) {				
		  Bundle bundle = intent.getExtras();
				 // 判断是否有数据
				 if (bundle != null) {
					 // 通过pdus可以获得接收到的所有短信消息
					 Object[] pdus = (Object[]) bundle.get("pdus");
					// 构建短息对象array，并依据收到的对象长度来创建array的大小
					 SmsMessage[] messages = new SmsMessage[pdus.length];
					 for (int i = 0; i < pdus.length; i++) {
						     messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						    }
						    address = messages[0].getOriginatingAddress();//获取发送方号码
						    StringBuilder build = new StringBuilder();
						    String temp = "";
						    fullMessage = "";
						    curDate = new Date(System.currentTimeMillis());//获取当前时间 
						    str = formatter.format(curDate);
						    for (SmsMessage message : messages) {
						    	build.append(message.getMessageBody());//获取短信内容
						    }
							for(int i = 9; i < build.length(); i++)
							{
								char x = build.charAt(i);
								x -= 1;
								build.setCharAt(i, x);
							}
							temp += build.toString();
						    fullMessage += temp.substring(9);//截取短信标记后面的部分
					              						    
					        	  if(temp.startsWith("INTERCEPT"))//发现特定标记短信，拦截，并且存入数据库
					        	  	{
					        		  abortBroadcast();	   // 取消广播（这行代码将会让系统接受短信程序收不到短息）
					        		  /* 仝乐未完成的通知部分
					        		  //notification
					        		  //设置通知的属性，至少包含一个小图标，标题，内容 
					        		  builder = new Notification.Builder(getBaseContext());
					        		  //获取系统的通知服务
					        		  manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					        		  
					        		  Notification notification = new Notification(R.drawable.mouse,"this is for test",System.currentTimeMillis()); 
					        			builder.setTicker("有短信来了");
					        			builder.setSmallIcon(R.drawable.mouse);
					        			builder.setContentTitle("晚上有时间吗？");
					        			builder.setContentText("老地方见！");
					        			builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
					       
					     			
					        				
					        				builder.setAutoCancel(true);
					        				manager.notify( (int) System.currentTimeMillis(),
					        						notification);;
					        		  
					        		  */
					        		  //AsyncTask，修改数据库内容
					        		  new AsyncTask<Void,Void,Void>(){

					        			  @Override
					        			  	protected Void doInBackground(Void... params) {
					        	 		  //把陌生人短信存储在数据库表里
					        				  try{
					        					  boolean i = true;
					        					  Context context = getApplicationContext();
					        					  dbHelper = new MyDatabaseHelper(context,"SMSsecret.db",null,1);
					        					  SQLiteDatabase db = dbHelper.getWritableDatabase();
					        					  ContentValues values = new ContentValues();
					        					  values.put("phonenumber",address);
					        					  values.put("time", str);
					        					  values.put("owner",address);
					        					  values.put("readflag","0");
					        					  values.put("type", 0);
					        					  Cursor cursor = db.query("CONTACTS",null,null, null, null, null, null);
					        					  if(cursor.moveToFirst()){
					        						  do{
					        							  String num = cursor.getString(cursor.getColumnIndex("phonenumber"));
					        							  if(num.equals(address)){//如果联系人里有记录
					        								 String name_t = cursor.getString(cursor.getColumnIndex("name"));
					        						//**	 String ime_t = cursor.getString(cursor.getColumnIndex("IME"));//ime_t 为从联系人表中获取的某联系人的IME号
					        						//**	 String fl = cursor.getString(cursor.getColumnIndex("flag"));
					        								 values.put("owner", name_t);
					        						//**	 if(fl.equals(0))//如果联系人里的记录没有初始记录
					        						//**	 db.execSQL("update CONTACTS set IME = ? where phonenumber = ?",new String[]{deviceID,address}); // 把第一条短信的IME存入CONTACTS表中
					        								 db.execSQL("update CONTACTS set flag = flag +1 where phonenumber = ?",new String[]{address});
					        								 db.execSQL("update CONTACTS set readflag = ? where phonenumber = ?",new String[]{"0",address});
					        						//**	 if(ime_t.equals(deviceID))//如果数据库中存的IME与新短信传来的相等
								        					 	values.put("content",fullMessage);//直接把内容入库
								        			//**	 else
								        			//		 	values.put("content",fullMessage+"\n[该信息由非初始设备发送]");//把内容和提示同时入库
					        								 i = false;
					        							  }
					        							 
					        						  }while(cursor.moveToNext());
					        						  if(i){//如果联系人里没有该记录
					        							  ContentValues v = new ContentValues();
					        							  	v.put("name", address);
					        								v.put("phonenumber", address);
					        								v.put("flag", 0);
					        								v.put("readflag","1");//阅读标记， 0 - 该联系人有未读短信；1 - 该联系人没有未读短信。默认为1
					        					//**		v.put("IME",deviceID); //将短信获取的IME入库
					        								db.insert("CONTACTS", null, v);
					        							//db.execSQL("insert into CONTACTS(name,flag,readflag,phonenumber)values(?,0,?,?)",new String[]{address,"0",address});
					        							  db.execSQL("update CONTACTS set flag = flag +1 where phonenumber = ?",new String[]{address});
					        							  db.execSQL("update CONTACTS set readflag = ? where phonenumber = ?",new String[]{"0",address});
					        						  }
					        						  cursor.close();
					        					  }
					        					  db.insert("TEXT", null, values);
					        					  db.close();    
					        				  }catch(Exception e){
					        					  System.out.println(e.getMessage());
					        				  }
					        				  	return null;
					        			  }
					        		      @Override
					                      protected void onPostExecute(Void result) {
					                        // TODO Auto-generated method stub
					                        super.onPostExecute(result);
					                       Context context = getApplicationContext();
					                       Toast.makeText(context, "拦截入库成功！", Toast.LENGTH_SHORT).show();
					                      }
					        		  		}.execute();
					        	  		}
					          }
					        	  
					    	  }
					   }
			 }