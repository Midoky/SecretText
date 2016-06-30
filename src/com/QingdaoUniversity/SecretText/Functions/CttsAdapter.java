/*������  
 * ʱ�䣺2016��4��5��13:12:14
 * ���ܣ�������
 * ��ϸ���ܣ���ϵ���б�
 */
package com.QingdaoUniversity.SecretText.Functions;

import java.util.List;

import com.example.uuu.R;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CttsAdapter extends ArrayAdapter<Ctts> {
	private int resourceId;
	

	public CttsAdapter(Context context,int textViewResourceId,List<Ctts> objects){
		super(context, textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		Ctts Ctts = getItem(position);//��ȡ��ǰ����ʵ��
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView Ctts_name = (TextView)view.findViewById(R.id.ctts_name);
		if(Ctts.getReadflag().equals("0")){//δ��
			Ctts_name.setText(" "+ Ctts.getName()+"  ("+Ctts.getFlag()+")"+"   ������Ϣ��");
			Ctts_name.setTextColor(android.graphics.Color.BLACK);
			TextPaint tp = Ctts_name.getPaint();
			tp.setFakeBoldText(true);
		}
		else if(Ctts.getReadflag().equals("1")){//�Ѷ�
			Ctts_name.setText(" " +Ctts.getName()+"  ("+Ctts.getFlag()+") ");
			Ctts_name.setTextColor(android.graphics.Color.DKGRAY);
		}
		return view;
	}

}

