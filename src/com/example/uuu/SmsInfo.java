/*������   2016��4��2��16:04:57
 * ���ܣ�������Ϣ
 * ��ϸ���ܣ���ȡ���ŵĿ��ñ�������
 * 
 * ****************
 * ******������*****
 * ****************
 */

package com.example.uuu;

public class SmsInfo {
    /**
     * ��������
     */
    private String smsbody;
    /**
     * ���Ͷ��ŵĵ绰����
     */
//   private String phoneNumber;
    /**
     * ���Ͷ��ŵ����ں�ʱ��
     */
    private String time;
    /**
     * ���Ͷ����˵�����
     */
    private String name;
    /**
     * ��������
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
