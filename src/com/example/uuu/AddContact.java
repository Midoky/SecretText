/*王戌琦  2016年4月4日16:01:53
 * 功能：添加联系人
 * 详细介绍：添加联系人，如果已存在相同号码，提示出错，如果存在相同联系人，则修改为当前输入号码。如果不存在，则直接入库，退出程序，提示添加成功。
 */

package com.example.uuu;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity{
	private MyDatabaseHelper dbHelper;
	private EditText contact;
	private EditText phonenumber;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.createnewcontact);
		contact = (EditText)findViewById(R.id.create_contact);
		phonenumber = (EditText)findViewById(R.id.create_phonenumber);
		submit = (Button)findViewById(R.id.create_submit);
		submit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Check_Add_Contact();
			finish();			
			}
		});
	}
	private void Check_Add_Contact(){
		dbHelper = new MyDatabaseHelper(this,"SMSsecret.db",null,1);
		Cursor cursor =null;
		boolean i = true;//标记变量，如果i= 0则不需要添加联系人到数据库
		String phnum = phonenumber.getText().toString();
		String phcts = contact.getText().toString();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
			//查询联系人数据
		cursor = db.query("CONTACTS",null,null,null,null,null,null);
			if(cursor.moveToFirst()){
				do{
					String number = cursor.getString(cursor.getColumnIndex("phonenumber"));
					String _name = cursor.getString(cursor.getColumnIndex("name"));
					if(number.equals(phnum)){  //如果号码存在，修改联系人名字
						ContentValues values = new ContentValues();
						values.put("name",phcts);//key为字段名，value为值
						db.update("CONTACTS", values, "phonenumber=?", new String[]{phnum});
						db.close();
						Toast.makeText(AddContact.this, "修改联系人信息成功!", Toast.LENGTH_SHORT).show();
						//Toast.makeText(AddContact.this, "该号码已存在，提交失败！", Toast.LENGTH_SHORT).show();
						i = false;
						break;
						}
					else if(_name.equals(phcts)){    //如果联系人已存在，则修改号码
						
						ContentValues values = new ContentValues();
						values.put("phonenumber",number);//key为字段名，value为值
						db.update("CONTACTS", values, "name=?", new String[]{phcts});
						db.close();
						Toast.makeText(AddContact.this, "已将该联系人"+phcts+"的号码修改为"+phnum, Toast.LENGTH_SHORT).show();
						i = false;
						break;
					}
					}while(cursor.moveToNext());
				}
			cursor.close();
			if(i){
			ContentValues values = new ContentValues();
			values.put("name", contact.getText().toString());
			values.put("phonenumber", phonenumber.getText().toString());
			values.put("flag", 0);
			values.put("readflag","1");//阅读标记， 0 - 该联系人有未读短信；1 - 该联系人没有未读短信。默认为1
			db.insert("CONTACTS", null, values);
			db.close();
			Toast.makeText(AddContact.this, "添加私密联系人成功!", Toast.LENGTH_SHORT).show();
		}
	}
	protected void onDestroy(){
		super.onDestroy();
	}
}
