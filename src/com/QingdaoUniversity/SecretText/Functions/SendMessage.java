package com.QingdaoUniversity.SecretText.Functions;
/* 史永飞   2016年4月2日16:04:57
 * 功能：短信发送
 * 详细介绍：发送加密短信
 * ****************
 * ******已弃用*****
 * ****************
 */
/*
package com.example.uuu;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends Activity {
    private EditText to;
    private EditText msgInput;
    private Button send;
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sendmessage_layout);
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver,sendFilter);
        to       = (EditText) findViewById(R.id.to);
        msgInput = (EditText) findViewById(R.id.msg_input);
        send     = (Button) findViewById(R.id.send);
        send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SmsManager smsManager = SmsManager.getDefault();
				Intent sentIntent = new Intent("SENT_SMS_ACTION");
				PendingIntent pi = PendingIntent.getBroadcast(SendMessage.this, 0, sentIntent, 0);
				StringBuilder build1 = new StringBuilder();
				build1.append("INTERCEPT");
			    build1.append(msgInput.getText().toString());
			    for(int i = 9; i < build1.length(); i++)//对短信内容进行加密  编码后移一位  真正的内容从第九位开始
			    {
					char x = build1.charAt(i);
					x += 1;
					build1.setCharAt(i, x);
				}
			    final String sendThings = build1.toString();
				smsManager.sendTextMessage(to.getText().toString(), null,sendThings, pi, null);
	        	smsManager.sendTextMessage(to.getText().toString(), null,sendThings, null, null);
			}
		});
}
	 @Override
	    protected void onDestroy()
	    {
	    	super.onDestroy();
	    	unregisterReceiver(sendStatusReceiver);
	    }
	 class SendStatusReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(getResultCode()==RESULT_OK){
				Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(context, "发送失败", Toast.LENGTH_LONG).show();
			}
		}
		 
	 }
}
*/
