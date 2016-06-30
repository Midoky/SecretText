/*������   2016��3��16��
 * ���ܣ���½�������ݿ�
 * ��ϸ���ܣ�����Ϊ9408������ֱ�ӽ���ϵͳ����
 */

package com.QingdaoUniversity.SecretText.Functions;

import com.example.uuu.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginNotification extends Activity{
	
	private EditText CText;
	@Override
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login1);
		CText = (EditText)findViewById(R.id.n_cText);
		Button bnLogin = (Button)findViewById(R.id.n_bnLogin);
		
		bnLogin.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	String text = CText.getText().toString();
				SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
	        	String str = sp.getString("key", "0000");
				if(str.equals(CText.getText().toString()))
				{
					Intent intent = new Intent(LoginNotification.this,CttsList.class);
					startActivity(intent);
					finish();
				}
				else 
				{
					  Intent intent = new Intent(Intent.ACTION_MAIN);
					  intent.setType("vnd.android-dir/mms-sms");
					  startActivity(intent);
				}
				
			}
		});
	}
	protected void onDestroy(){
		super.onDestroy();
	}
}

