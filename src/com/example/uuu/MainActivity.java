/*王戌琦   2016年4月20日
 * 功能：主菜单
 * 详细介绍：各种功能按键集合。
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
		titleText.setText("隐私短信");
		Intent intent = new Intent(this, MsgService.class);
		startService(intent);
		//这个if用来判断是否需要启动login
/*		if(true){//if里还需要加代码
			Intent intent2 = new Intent(this,Login.class);
			startActivity(intent2);
			finish();
		}
		*/
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,CttsList.class);//登陆需要修改
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

	                Toast.makeText(this,"再按一次退出程序" ,Toast.LENGTH_SHORT).show();
	                mExitTime = System.currentTimeMillis();

	            } else {
	                finish();
	            }
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
}
	//从系统联系人中获取联系人
/*	private void getContacts(){//读取系统联系人存入app数据库
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
		Cursor cursor =null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try{
			//查询联系人数据
			cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			if(cursor.moveToFirst()){
			do{
				//获取联系人姓名
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				//获取联系人号码
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				ContentValues values = new ContentValues();
				values.put("name", name);
				values.put("phonenumber",number);
				values.put("flag", "2");//没有消息
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

   