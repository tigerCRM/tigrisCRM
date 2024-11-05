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
package com.example.crm.core.mapper;

import java.util.List;

/**
 * <code>GenericMapper.java</code>
 *
 * @author Jaehwan Lee
 * @since 2016. 3. 17.
 * @version 1.0
 */
public interface GenericMapper <E> {

	int insert(E entity);

	int insertList(List<E> entity);

	int update(E entity);

	int delete(E entity);

}
