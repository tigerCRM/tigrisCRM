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
package com.example.crm.core.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <code>DateUtils</code>
 *
 * @author Jaehwan Lee
 * @since 2016. 4. 22
 * @version 1.0
 */
@Slf4j
public class DateUtils {

	private final static String DEFAULT_TIMEZONE_ID = "Asia/Seoul";


	private DateUtils() {
	}

	public static String  getExpireYn(String date, String expireHm){
		String formatDate = date.substring(0, 10);
		formatDate += " " + expireHm;
		String resultYn = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmm");
		try {
			Date expireDate = formatter.parse(formatDate);
			Date currentDate = new Date();

			if(currentDate.getTime() > expireDate.getTime()){
				resultYn = "Y";
			}else{
				resultYn = "N";
			};
		} catch (ParseException e) {
			log.debug("Parse Exception");
			return null;
		}
		return resultYn;
	}
	public static String  getFormatDateString(String format, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 포멧된 date 가져오기
	 * @param format
	 * @return
	 */
	public static String  getCurrentDateFormat(String format){

		//기본포멧 설정
		format  = ( format == null || format.isEmpty() ) ? "yyyy-MM-dd HH:mm:ss": format;

		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(now);
	}

	/**
	 * todayDt가 startDt와 endDt사이에 존재하는지 구하는 메서
	 *
	 * @param todayDt
	 * @param startDt
	 * @param endDt
	 * @return
	 */
	public static boolean isIncludeStartAndEndDate(Date todayDt, Date startDt, Date endDt) {
		boolean result = false;
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDt);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDt);

		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(todayDt);
		LocalDate todayDate = LocalDate.of(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH) + 1, todayCalendar.get(Calendar.DAY_OF_MONTH));

		LocalDate startDate = LocalDate.of(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));
		LocalDate endDate = LocalDate.of(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH) + 1, endCalendar.get(Calendar.DAY_OF_MONTH));

		// endDate가 startDate보다 적을수 없다.
		if (startDate.isBefore(endDate)) {
			if (startDate.isBefore(todayDate) && endDate.isAfter(todayDate)) {
				result = true;
			} else {
				if (startDate.isEqual(todayDate) || endDate.isEqual(todayDate)) {
					result = true;
				}
			}
		} else {
			if (startDate.isEqual(endDate)) {
				if (startDate.isEqual(todayDate)) {
					result = true;
				}
			}
		}
		return result;
	}

	public static String getDateTime(Date date, String langCode, String timeZoneId) {

		if(date == null) return "";
		String datetimeFormat = "yyyy-MM-dd HH:mm";
		SimpleDateFormat formatter = new SimpleDateFormat(datetimeFormat, new Locale(langCode));

		if(StringUtils.isEmpty(timeZoneId)) {
			timeZoneId = DateUtils.DEFAULT_TIMEZONE_ID;
		}

		formatter.setTimeZone(TimeZone.getTimeZone(timeZoneId));

		return formatter.format(date);
	}

	public static String getDateTimeDetail(Date date, String langCode, String timeZoneId) {

		if(date == null) return "";
		String datetimeFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat formatter = new SimpleDateFormat(datetimeFormat, new Locale(langCode));

		if(StringUtils.isEmpty(timeZoneId)) {
			timeZoneId = DateUtils.DEFAULT_TIMEZONE_ID;
		}

		formatter.setTimeZone(TimeZone.getTimeZone(timeZoneId));

		return formatter.format(date);
	}

	public static String getDateToString(Date date, String format)
	{
		//기본포멧 설정
		format  = ( format == null || format.isEmpty() ) ? "yyyy-MM-dd HH:mm:ss": format;

		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}

}
