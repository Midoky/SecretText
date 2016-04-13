/*������  2016��4��4��16:01:53
 * ���ܣ������ϵ��
 * ��ϸ���ܣ������ϵ�ˣ�����Ѵ�����ͬ���룬��ʾ�������������ͬ��ϵ�ˣ����޸�Ϊ��ǰ������롣��������ڣ���ֱ����⣬�˳�������ʾ��ӳɹ���
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
		boolean i = true;//��Ǳ��������i= 0����Ҫ�����ϵ�˵����ݿ�
		String phnum = phonenumber.getText().toString();
		String phcts = contact.getText().toString();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
			//��ѯ��ϵ������
		cursor = db.query("CONTACTS",null,null,null,null,null,null);
			if(cursor.moveToFirst()){
				do{
					String number = cursor.getString(cursor.getColumnIndex("phonenumber"));
					String _name = cursor.getString(cursor.getColumnIndex("name"));
					if(number.equals(phnum)){  //���������ڣ��޸���ϵ������
						ContentValues values = new ContentValues();
						values.put("name",phcts);//keyΪ�ֶ�����valueΪֵ
						db.update("CONTACTS", values, "phonenumber=?", new String[]{phnum});
						db.close();
						Toast.makeText(AddContact.this, "�޸���ϵ����Ϣ�ɹ�!", Toast.LENGTH_SHORT).show();
						//Toast.makeText(AddContact.this, "�ú����Ѵ��ڣ��ύʧ�ܣ�", Toast.LENGTH_SHORT).show();
						i = false;
						break;
						}
					else if(_name.equals(phcts)){    //�����ϵ���Ѵ��ڣ����޸ĺ���
						
						ContentValues values = new ContentValues();
						values.put("phonenumber",number);//keyΪ�ֶ�����valueΪֵ
						db.update("CONTACTS", values, "name=?", new String[]{phcts});
						db.close();
						Toast.makeText(AddContact.this, "�ѽ�����ϵ��"+phcts+"�ĺ����޸�Ϊ"+phnum, Toast.LENGTH_SHORT).show();
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
			values.put("readflag","1");//�Ķ���ǣ� 0 - ����ϵ����δ�����ţ�1 - ����ϵ��û��δ�����š�Ĭ��Ϊ1
			db.insert("CONTACTS", null, values);
			db.close();
			Toast.makeText(AddContact.this, "���˽����ϵ�˳ɹ�!", Toast.LENGTH_SHORT).show();
		}
	}
	protected void onDestroy(){
		super.onDestroy();
	}
}
