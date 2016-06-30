/*王戌琦  
 * 时间： 2016年4月2日16:04:57
 * 功能：联系人信息
 * 详细介绍：为listview 提供一个类
 */

package com.QingdaoUniversity.SecretText.Functions;

public class Ctts {
	    /**
	     * 发送短信的电话号码
	     */
	   private String phoneNumber;
	    /**
	     * 发送短信人的姓名
	     */
	    private String name;
	    /**
	     * 短信类型(已读/未读）
	     */
	    private String readflag;
	    /**
	     * 短信数量
	     */
	    private String flag;
	    
	    public String getName() {
	        return name;
	    }
	    public String getphoneNumber() {
	        return phoneNumber;
	    }

	    public String getFlag(){
	    	return flag;
	    }
	    public String getReadflag() {
	        return readflag;
	    }
	    public Ctts(String name,String phoneNumber,String flag,String readflag)
	    {
	    	this.name = name;
	    	this.phoneNumber = phoneNumber;
	    	this.flag = flag;
	    	this.readflag = readflag;
	    }
}
