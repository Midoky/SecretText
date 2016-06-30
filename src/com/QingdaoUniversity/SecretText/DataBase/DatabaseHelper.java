/*������   2016��4��2��16:04:57
 * ���ܣ����ݿ�
 * ��ϸ���ܣ��������
 */

package com.QingdaoUniversity.SecretText.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_TEXT = "create table TEXT ("//�����б�
			+ "id integer primary key autoincrement ,"
			+ "phonenumber text,"
			+ "readflag text,"			//�Ķ����  0-δ��   1-�Ѷ�
			+ "owner text,"				//��ϵ���б��Ƿ��иú��룬���򽫸�����������owner
			+ "time text,"
			+ "type integer,"				//�������� 0 - �յ��Ķ��ţ� 1- �����Ķ���
			+ "content text)";
	public static final String CREATE_CONTACTS = "create table CONTACTS ("//��ϵ�˱�
			+"id integer primary key autoincrement,"
			+"name text,"
			+"flag integer,"//�����������  
			+"readflag text,"//�����Ѷ���� 0 - δ���� 1 - �Ѷ�
			+"IME text,"//�豸��Ψһ���к�
			+"phonenumber text)";
		private Context mContext;
		public DatabaseHelper(Context context , String name,CursorFactory factory,int version){
			super(context ,name , factory,version);
			mContext = context;
		}
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(CREATE_TEXT);
			db.execSQL(CREATE_CONTACTS);
			Toast.makeText(mContext, "��ӭʹ����˽����!", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion ,int newVersion){
		}

}