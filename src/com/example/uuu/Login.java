/*������   2016��3��16��
 * ���ܣ���½�������ݿ�
 * ��ϸ���ܣ�����Ϊ9408������ֱ�ӽ���ϵͳ����
 */

package com.example.uuu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	
	private EditText CText;
	@Override
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		CText = (EditText)findViewById(R.id.cText);
		Button bnLogin = (Button)findViewById(R.id.bnLogin);
		
		bnLogin.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	String text = CText.getText().toString();
				if("9408".equals(CText.getText().toString()))
				{
					Intent intent = new Intent(Login.this,MainActivity.class);
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

