/* 王戌琦
 * 时间：2016年4月4日16:49:35
 * 代码功能：监听开机广播，启动拦截短信服务
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
