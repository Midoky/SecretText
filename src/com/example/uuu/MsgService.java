/* ���غʹ���ʷ����   
 * ��������ݿ⣺������
 * ֪ͨ������
 * ʱ�䣺2016��4��5��13:57:10
 * ���ܣ����ض��ţ����ܣ���⣬֪ͨ��ʾ
 * ��ϸ���ܣ�����ע��
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
	    //ע�ᶯ̬�㲥
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
	    //ȡ���㲥������
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
	  // �����յ���Ϣʱ������
	  @SuppressLint("NewApi")
	@Override
	  public void onReceive(Context context, Intent intent) {				
		  Bundle bundle = intent.getExtras();
				 // �ж��Ƿ�������
				 if (bundle != null) {
					 // ͨ��pdus���Ի�ý��յ������ж�����Ϣ
					 Object[] pdus = (Object[]) bundle.get("pdus");
					// ������Ϣ����array���������յ��Ķ��󳤶�������array�Ĵ�С
					 SmsMessage[] messages = new SmsMessage[pdus.length];
					 for (int i = 0; i < pdus.length; i++) {
						     messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						    }
						    address = messages[0].getOriginatingAddress();//��ȡ���ͷ�����
						    StringBuilder build = new StringBuilder();
						    String temp = "";
						    fullMessage = "";
						    curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ�� 
						    str = formatter.format(curDate);
						    for (SmsMessage message : messages) {
						    	build.append(message.getMessageBody());//��ȡ��������
						    }
							for(int i = 9; i < build.length(); i++)
							{
								char x = build.charAt(i);
								x -= 1;
								build.setCharAt(i, x);
							}
							temp += build.toString();
						    fullMessage += temp.substring(9);//��ȡ���ű�Ǻ���Ĳ���
					              						    
					        	  if(temp.startsWith("INTERCEPT"))//�����ض���Ƕ��ţ����أ����Ҵ������ݿ�
					        	  	{
					        		  abortBroadcast();	   // ȡ���㲥�����д��뽫����ϵͳ���ܶ��ų����ղ�����Ϣ��
					        		  /* ����δ��ɵ�֪ͨ����
					        		  //notification
					        		  //����֪ͨ�����ԣ����ٰ���һ��Сͼ�꣬���⣬���� 
					        		  builder = new Notification.Builder(getBaseContext());
					        		  //��ȡϵͳ��֪ͨ����
					        		  manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					        		  
					        		  Notification notification = new Notification(R.drawable.mouse,"this is for test",System.currentTimeMillis()); 
					        			builder.setTicker("�ж�������");
					        			builder.setSmallIcon(R.drawable.mouse);
					        			builder.setContentTitle("������ʱ����");
					        			builder.setContentText("�ϵط�����");
					        			builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
					       
					     			
					        				
					        				builder.setAutoCancel(true);
					        				manager.notify( (int) System.currentTimeMillis(),
					        						notification);;
					        		  
					        		  */
					        		  //AsyncTask���޸����ݿ�����
					        		  new AsyncTask<Void,Void,Void>(){

					        			  @Override
					        			  	protected Void doInBackground(Void... params) {
					        	 		  //��İ���˶��Ŵ洢�����ݿ����
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
					        							  if(num.equals(address)){//�����ϵ�����м�¼
					        								 String name_t = cursor.getString(cursor.getColumnIndex("name"));
					        						//**	 String ime_t = cursor.getString(cursor.getColumnIndex("IME"));//ime_t Ϊ����ϵ�˱��л�ȡ��ĳ��ϵ�˵�IME��
					        						//**	 String fl = cursor.getString(cursor.getColumnIndex("flag"));
					        								 values.put("owner", name_t);
					        						//**	 if(fl.equals(0))//�����ϵ����ļ�¼û�г�ʼ��¼
					        						//**	 db.execSQL("update CONTACTS set IME = ? where phonenumber = ?",new String[]{deviceID,address}); // �ѵ�һ�����ŵ�IME����CONTACTS����
					        								 db.execSQL("update CONTACTS set flag = flag +1 where phonenumber = ?",new String[]{address});
					        								 db.execSQL("update CONTACTS set readflag = ? where phonenumber = ?",new String[]{"0",address});
					        						//**	 if(ime_t.equals(deviceID))//������ݿ��д��IME���¶��Ŵ��������
								        					 	values.put("content",fullMessage);//ֱ�Ӱ��������
								        			//**	 else
								        			//		 	values.put("content",fullMessage+"\n[����Ϣ�ɷǳ�ʼ�豸����]");//�����ݺ���ʾͬʱ���
					        								 i = false;
					        							  }
					        							 
					        						  }while(cursor.moveToNext());
					        						  if(i){//�����ϵ����û�иü�¼
					        							  ContentValues v = new ContentValues();
					        							  	v.put("name", address);
					        								v.put("phonenumber", address);
					        								v.put("flag", 0);
					        								v.put("readflag","1");//�Ķ���ǣ� 0 - ����ϵ����δ�����ţ�1 - ����ϵ��û��δ�����š�Ĭ��Ϊ1
					        					//**		v.put("IME",deviceID); //�����Ż�ȡ��IME���
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
					                       Toast.makeText(context, "�������ɹ���", Toast.LENGTH_SHORT).show();
					                      }
					        		  		}.execute();
					        	  		}
					          }
					        	  
					    	  }
					   }
			 }