/*
 * Copyright (c) 2011-2016, TigerCompany. All rights reserved.
 * http://www.tigrison.com
 * http://www.tigersw.com
 * http://www.tigercompany.kr
 *
 * This software is the confidential and proprietary information of
 * TigerCompany. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with TigerCompany.
 */
package com.example.crm.core.context;

/**
 * <code>Profile</code>
 *
 * @author Jaehwan Lee
 * @since 2016. 3. 17.
 * @version 1.0
 */
public enum Profile {

	LOCAL("local"),
	DEV("dev"),
	TEST("test"),
	PROD("prod");

	private String value;

	private Profile(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
