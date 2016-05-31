package com.example.uuu;

import com.example.uuu.MsgService.ReceiveMessage;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {
	CheckBox icb1 = null;
	CheckBox icb2 = null;
	CheckBox icb3 = null;
	private ReceiveMessage mReceiver;
	private Notification.Builder builder;
	private NotificationManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings);
		TextView titleText = (TextView)findViewById(R.id.settings_title);
		titleText.setText("设置");
		
		
		icb1 = (CheckBox)findViewById(R.id.checkBox1);
		icb2 = (CheckBox)findViewById(R.id.checkBox2);
		icb3 = (CheckBox)findViewById(R.id.checkBox3);
		
		Button about = (Button) findViewById(R.id.about);
		Button submit = (Button) findViewById(R.id.submit_newkey);
		Button button1 = (Button) findViewById(R.id.button1);
		
		boolean isChecked1;
		boolean isChecked2;
		boolean isChecked3;
		
		
		
		
		
		SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
    	isChecked1 = sp.getBoolean("isChecked1", false);
    	icb1.setChecked(isChecked1);
    	
    	isChecked2 = sp.getBoolean("isChecked2", false);
    	icb2.setChecked(isChecked2);
    	
    	isChecked3 = sp.getBoolean("isChecked3", false);
    	icb3.setChecked(isChecked3);
    	
		icb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            //@Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
            	SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
            	
            	Editor ed = sp.edit();
            	ed.putBoolean("isChecked1", isChecked);
            	icb1.setChecked(isChecked);
            	ed.commit();
            } 
        }); 
		
		icb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            //@Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
            	SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
            	
            	Editor ed = sp.edit();
            	ed.putBoolean("isChecked2", isChecked);
            	icb2.setChecked(isChecked);
            	ed.commit();
            } 
        }); 
		
		icb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            //@Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
            	SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
            	
            	Editor ed = sp.edit();
            	ed.putBoolean("isChecked3", isChecked);
            	icb3.setChecked(isChecked);
            	ed.commit(); 
            } 
        }); 
		about.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settings.this,About.class);//转到关于页面
				startActivity(intent);
			}
		} );
		submit.setOnClickListener(new OnClickListener(){
			
			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText editText = (EditText)findViewById(R.id.keys);
				SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
            	
            	Editor ed = sp.edit();
            	ed.putString("key", editText.getText().toString());
            	ed.commit(); 
            	Toast.makeText(getApplicationContext(), "修改密码成功！", Toast.LENGTH_SHORT).show(); 
			}
		});
		/*
		button1.setOnClickListener(new OnClickListener(){
			
			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.drawable.mouse);
                Notification.Builder mBuilder = new Notification.Builder(
                        Settings.this).setSmallIcon(R.drawable.mouse)
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
              	  resultIntent = new Intent(Settings.this,
              			  MainActivity.class);
                }
                else{
              	  resultIntent = new Intent(Settings.this,
              			  Login_notification.class);
                }
                
                //封装一个Intent
                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        Settings.this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                // 设置通知主题的意图
                mBuilder.setContentIntent(resultPendingIntent);
                //获取通知管理器对象
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(1, notification);
      		  
				
			}
		});
		*/
}
}