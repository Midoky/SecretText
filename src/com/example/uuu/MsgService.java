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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.uuu.Settings;

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
						    if(address.startsWith("+86"))
						    {
						    	address = address.substring(3);
						    }
						    StringBuilder build = new StringBuilder();
						    StringBuilder build1 = new StringBuilder();
						    String temp = "";
						    fullMessage = "";
						    curDate = new Date(System.currentTimeMillis());//获取当前时间 
						    str = formatter.format(curDate);
						    //将发送短信的内容前个字符做哈希，放到短信内容的前面做位标记
						    for (SmsMessage message : messages) {
						    	build.append(message.getMessageBody());//获取短信内容
						    }
					 
							build1.append(build.substring(2, 4));//收到短信后截取短信第2,3位（下标从0开始），因为原来的0,1位现在是哈希之后的标志位
						    for(int i = 0; i < build1.length(); i++)
						    {
						    	char x = build1.charAt(i);//将2,3位解密 之后哈希 与 收到短信的0,1位比较
						    	x -= 1;
						    	build1.setCharAt(i,x);
						    }
						    String str_build1 = build1.toString().hashCode()+"";
						    String str_build1_2 = str_build1.substring(0, 2);
						    temp += build.toString();
						    fullMessage += temp.substring(2);//截取短信标记后面的部分 ，现在fullmessage里面都是加密后的短信内容没有哈希标记位
  						    
//						    StringBuilder build3 = new StringBuilder();
//							build3.append(fullMessage); //content来源于 service里面的fullmessage 那时候已经从第2位（下标从0 开始）截取了，所以不包含标志位，只是单纯的加密后的短信内容
//							for(int i = 0; i < build3.length(); i++)
//							{									
//								    char x = build3.charAt(i);
//									x -= 1;
//									build3.setCharAt(i, x);
//						    }
					
						    
				        	  if(temp.startsWith(str_build1_2))//发现特定标记短信，拦截，并且存入数据库
					        	  	{
					        		  abortBroadcast();	   // 取消广播（这行代码将会让系统接受短信程序收不到短息）
					        		  Bitmap btm = BitmapFactory.decodeResource(getResources(),
					                          R.drawable.mouse);
					                  Notification.Builder mBuilder = new Notification.Builder(
					                          MsgService.this).setSmallIcon(R.drawable.mouse)
					                          .setContentTitle("1 new message")
					                          .setDefaults( Notification.DEFAULT_SOUND)
					                          .setContentText("注意天气变化，关注今日天气");
					                  mBuilder.setTicker("天气提示");//第一次提示消息的时候显示在通知栏上
					                  mBuilder.setNumber(12);
					                  mBuilder.setLargeIcon(btm);
					                  mBuilder.setAutoCancel(true);//自己维护通知的消失
					                  
					                  
					                  //构建一个Intent 默认使用密码打开
					                  Intent resultIntent;
					                 
					                  boolean isChecked;
					              	  SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
					              	  isChecked = sp.getBoolean("isChecked2", true);
					                  
					                  if( isChecked ){
					                	  //如果选中则直接进入主界面
					                	  resultIntent = new Intent(MsgService.this,
					                			  CttsList.class);
					                  }
					                  else{
					                	  resultIntent = new Intent(MsgService.this,
					                			  Login_notification.class);
					                  }
					                  
					                  //封装一个Intent
					                  PendingIntent resultPendingIntent = PendingIntent.getActivity(
					                          MsgService.this, 0, resultIntent,
					                          PendingIntent.FLAG_UPDATE_CURRENT);
					                  // 设置通知主题的意图
					                  mBuilder.setContentIntent(resultPendingIntent);
					                  //获取通知管理器对象
					                  NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					                  Notification notification = mBuilder.build();
					                  notification.flags = Notification.FLAG_AUTO_CANCEL;
					                  mNotificationManager.notify(1, notification);
					        		  
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
					        					  values.put("content",fullMessage);//直接把内容入库
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
					                      // Toast.makeText(context, "拦截入库成功！", Toast.LENGTH_SHORT).show();
					                      }
					        		  		}.execute();
					        	  		}
					          }
					        	  
					    	  }
					   }
			 }