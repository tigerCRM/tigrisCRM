/*
 * Copyright (c) 2011-2015, TigerCompany. All rights reserved.
 * http://www.tigersw.com
 * http://www.tigercompany.kr
 * http://www.tigercompany.co.kr
 *
 * This software is the confidential and proprietary information of
 * TigerCompany. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with TigerCompany.
 */
package com.tiger.crm.common.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * <code>AES256Utils</code>
 * 양방향 암호화 알고리즘인 AES256 암호화를 지원하는 클래스
 *
 * @author 이정운(julee@tigercompany.kr)
 * @since 2015. 11. 16
 * @version 1.0
 */
public class AES256Utils {

    private String iv;
    private Key keySpec;

    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     * @param key 암/복호화를 위한 키값
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생
     */
    public AES256Utils(String key) throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);
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

    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     * @param str 복호화할 문자열
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }

    public static void main(String[] args)
    {
    	try
    	{
			AES256Utils AES256Utils = new AES256Utils("330ed346c97712232ba447f49b8846eb");

//			String 		linkUrl1 	= "f4b20c15249dc4656c3174cdc17b51ff:seastar@tigrison.com";
//			String 		linkUrl2 	= "f4b20c15249dc4656c3174cdc17b51ff:row2@mf.seegene.com";
//			System.out.println(AES256Utils.encrypt(linkUrl1));
//			System.out.println(AES256Utils.encrypt(linkUrl2));

//			String str = "aydhR437WUe404qm0opjZD02eSx+YeUum5v18g09aYQbho9uyOIvfWw0HbvJi2QXPUnmAxB+k0xrFhotb5T5xg==";
			String str = "uQuIMRr9Kpn7D3rawigKd+QkG5q/jRl/6WC1hnQL0DJndEG+1SMfrQRLvQ7MPfnDuyw2CAcGHzmGfSK62qA2U+y9yxMJUSWkK8C9ESJ+o1RBZ7NOWLiBc+58aCxZtuWc4fVaezUp2Kg5LoJ4Rzy0xYosn/mld7FsE/BmpHHPtuY=";
			System.out.println(AES256Utils.decrypt(str));


		}
    	catch (UnsupportedEncodingException | GeneralSecurityException e)
    	{
			e.printStackTrace();
		}
    }
}
