/*������  
 * ʱ�䣺 2016��4��2��16:04:57
 * ���ܣ���ϵ����Ϣ
 * ��ϸ���ܣ�Ϊlistview �ṩһ����
 */

package com.QingdaoUniversity.SecretText.Functions;

public class Ctts {
	    /**
	     * ���Ͷ��ŵĵ绰����
	     */
	   private String phoneNumber;
	    /**
	     * ���Ͷ����˵�����
	     */
	    private String name;
	    /**
	     * ��������(�Ѷ�/δ����
	     */
	    private String readflag;
	    /**
	     * ��������
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
