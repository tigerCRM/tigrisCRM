package com.tiger.crm.common.crypto;

import org.springframework.util.DigestUtils;

public class MD5Utils {

	private MD5Utils() {
	}

	public static String md5toHex(Object o) {
		return (o == null) ? null : DigestUtils.md5DigestAsHex(o.toString().getBytes());
	}
}
