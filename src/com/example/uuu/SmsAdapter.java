/*������   2016��4��2��16:04:57
 * ���ܣ�������
 * ��ϸ���ܣ��б���
 * ****************
 * ******������*****
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
		SmsInfo smsinfo = getItem(position);//��ȡ��ǰ����ʵ��
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView Smsname = (TextView)view.findViewById(R.id.tv_Sms_name);
		TextView Smsbody = (TextView)view.findViewById(R.id.tv_Sms_body);
		TextView Smstime = (TextView)view.findViewById(R.id.tv_Sms_time);
		if(smsinfo.getFlag().equals("0")){//δ��
			Smsname.setText("������:"+smsinfo.getName());
			Smsname.setTextColor(android.graphics.Color.BLACK);
			Smsbody.setText("����:"+smsinfo.getSmsbody());
			Smsbody.setTextColor(android.graphics.Color.BLACK);
			Smstime.setText("ʱ��:"+smsinfo.getTime());
			Smstime.setTextColor(android.graphics.Color.BLACK);
		}
		else if(smsinfo.getFlag().equals("1")){//�Ѷ�
			Smsname.setText("������:"+smsinfo.getName());
			Smsname.setTextColor(android.graphics.Color.GRAY);
			Smsbody.setText("����:"+smsinfo.getSmsbody());
			Smsbody.setTextColor(android.graphics.Color.GRAY);
			Smstime.setText("ʱ��:"+smsinfo.getTime());
			Smstime.setTextColor(android.graphics.Color.GRAY);
		}
		return view;
	}

}
*/