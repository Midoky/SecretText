/* ������
 * ʱ�䣺2016��4��4��16:49:35
 * ���빦�ܣ����������㲥���������ض��ŷ���
 */
package com.example.uuu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent){
		Intent _intent = new Intent(context, MsgService.class);
		context.startService(_intent);
	}
}
