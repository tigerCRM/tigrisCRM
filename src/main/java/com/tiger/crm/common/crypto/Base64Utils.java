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

/**
 * <code>Base64Utils</code>
 *
 * @author 이정운(julee@tigercompany.kr)
 * @since 2015. 11. 2
 * @version 1.0
 */
public class Base64Utils {

    private static final String CHARSET = "UTF-8";

    public static byte[] encodeBase64(byte[] binaryData) throws Exception {
        return Base64.encodeBase64(binaryData);
    }

    public static String encodeBase64String(byte[] binaryData) throws Exception {
        return new String(encodeBase64(binaryData), CHARSET);
    }

    public static String encodeBase64String(String source) throws Exception {
        return encodeBase64String(source, CHARSET);
    }

    public static String encodeBase64String(String source, String charset) throws Exception {
        return Base64.encodeBase64String(source.getBytes(charset));
    }

    public static byte[] decodeBase64(String base64String) throws Exception {
        return Base64.decodeBase64(base64String);
    }

    public static String decodeBase64String(String base64String) throws Exception {
        return decodeBase64String(base64String, CHARSET);
    }

    public static String decodeBase64String(String base64String, String charset) throws Exception {
        return new String(decodeBase64(base64String), charset);
    }

}
