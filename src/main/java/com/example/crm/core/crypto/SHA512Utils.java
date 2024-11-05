package com.example.crm.core.crypto;

import java.security.MessageDigest;

public class SHA512Utils {

	private SHA512Utils() {
	}

	/**
     * sha512 방식으로 암호화한 스트링을 리턴한다
     *
     * @param target
     * @return [encrypted string]
     */
    public final static String encrypt(String target) {
    	StringBuffer sb = new StringBuffer();
    	try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(target.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            //convert the byte to hex format method 2
            /*
            StringBuffer hexString = new StringBuffer();
        	for (int i=0;i<byteData.length;i++) {
        		String hex=Integer.toHexString(0xff & byteData[i]);
       	     	if(hex.length()==1) hexString.append('0');
       	     	hexString.append(hex);
        	}
        	*/

        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sb.toString();
    }

}
