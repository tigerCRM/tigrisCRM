package com.tiger.crm.common.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;


public class AES128Utils {

    private Key keySpec;

    public AES128Utils(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        this.keySpec = keySpec;
    }

    public String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    public String decrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[16]));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }

    public static void main(String[] args)
    {
    	try
    	{
			AES128Utils aes128Utils = new AES128Utils("6c6afe32f263c477");

			System.out.println("Enc userEmpNo : " + aes128Utils.encrypt("L22151"));
			System.out.println("Enc EMP_NO : " + aes128Utils.encrypt("9999999"));
		}
    	catch (UnsupportedEncodingException | GeneralSecurityException e)
    	{
			e.printStackTrace();
		}
    }

}
