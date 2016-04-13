/*王戌琦   2016年4月2日16:04:57
 * 功能：适配器
 * 详细介绍：列表辅助
 * ****************
 * ******已弃用*****
 * ****************
 */
/*
package com.example.uuu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SmsAdapter extends ArrayAdapter<SmsInfo> {
	private int resourceId;
	public SmsAdapter(Context context,int textViewResourceId,List<SmsInfo> objects){
		super(context, textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		SmsInfo smsinfo = getItem(position);//获取当前短信实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView Smsname = (TextView)view.findViewById(R.id.tv_Sms_name);
		TextView Smsbody = (TextView)view.findViewById(R.id.tv_Sms_body);
		TextView Smstime = (TextView)view.findViewById(R.id.tv_Sms_time);
		if(smsinfo.getFlag().equals("0")){//未读
			Smsname.setText("发送人:"+smsinfo.getName());
			Smsname.setTextColor(android.graphics.Color.BLACK);
			Smsbody.setText("内容:"+smsinfo.getSmsbody());
			Smsbody.setTextColor(android.graphics.Color.BLACK);
			Smstime.setText("时间:"+smsinfo.getTime());
			Smstime.setTextColor(android.graphics.Color.BLACK);
		}
		else if(smsinfo.getFlag().equals("1")){//已读
			Smsname.setText("发送人:"+smsinfo.getName());
			Smsname.setTextColor(android.graphics.Color.GRAY);
			Smsbody.setText("内容:"+smsinfo.getSmsbody());
			Smsbody.setTextColor(android.graphics.Color.GRAY);
			Smstime.setText("时间:"+smsinfo.getTime());
			Smstime.setTextColor(android.graphics.Color.GRAY);
		}
		return view;
	}

}
*/