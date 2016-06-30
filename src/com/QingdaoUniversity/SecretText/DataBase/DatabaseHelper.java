/*王戌琦   2016年4月2日16:04:57
 * 功能：数据库
 * 详细介绍：建表更新
 */

package com.QingdaoUniversity.SecretText.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_TEXT = "create table TEXT ("//短信列表
			+ "id integer primary key autoincrement ,"
			+ "phonenumber text,"
			+ "readflag text,"			//阅读标记  0-未读   1-已读
			+ "owner text,"				//联系人列表是否有该号码，有则将该人姓名置入owner
			+ "time text,"
			+ "type integer,"				//短信类型 0 - 收到的短信， 1- 发出的短信
			+ "content text)";
	public static final String CREATE_CONTACTS = "create table CONTACTS ("//联系人表
			+"id integer primary key autoincrement,"
			+"name text,"
			+"flag integer,"//短信数量标记  
			+"readflag text,"//短信已读标记 0 - 未读， 1 - 已读
			+"IME text,"//设备的唯一序列号
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
			Toast.makeText(mContext, "欢迎使用隐私短信!", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion ,int newVersion){
		}

}