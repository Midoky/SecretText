/*王戌琦   2016年4月2日16:04:57
 * 功能：短信消息
 * 详细介绍：提取短信的可用变量集合
 * 
 * ****************
 * ******已弃用*****
 * ****************
 */

package com.example.uuu;

public class SmsInfo {
    /**
     * 短信内容
     */
    private String smsbody;
    /**
     * 发送短信的电话号码
     */
//   private String phoneNumber;
    /**
     * 发送短信的日期和时间
     */
    private String time;
    /**
     * 发送短信人的姓名
     */
    private String name;
    /**
     * 短信类型
     */
    private String readflag;
    private String _id;
    
    public String getName() {
        return name;
    }
    public String getSmsbody() {
        return smsbody;
    }
    public String getTime(){
    	return time;
    }
    public String getID(){
    	return _id;
    }
    public String getFlag() {
        return readflag;
    }
    public SmsInfo(String name,String smsbody,String time,String _id,String readflag)
    {
    	this.name = name;
    	this.smsbody = smsbody;
    	this.time = time;
    	this._id = _id;
    	this.readflag = readflag;
    }
//    public void setSmsbody(String smsbody) {
//        this.smsbody = smsbody;
 //   }
/*
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
*/


//    public void setName(String name) {
//        this.name = name;
 //   }
/*


   public void setType(String type) {
        this.type = type;
   }
   */

}
