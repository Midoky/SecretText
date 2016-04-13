/*王戌琦   2016年4月2日16:04:57
 * 功能：短信消息
 * 详细介绍：提取短信的可用变量集合
 */

package com.example.uuu;

public class Msg {
	public static final int TYPE_RECEIVED = 0;
	public static final int TYPE_SENT = 1;
	private String content;
	private int type;
	private String time;
	public Msg(String content,int type,String time){
		this.content = content;
		this.type = type;
		this.time = time;
	}
	public String getContent(){
		return content;
	}
	public int getType(){
		return type;
	}
	public String getTime(){
		return time;
	}
}
