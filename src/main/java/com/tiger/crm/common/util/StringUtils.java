package com.tiger.crm.common.util;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static final String SPACE = " ";

    /** object를 string으로 변경해줌.
     * @param obj
     * @return
     */
    public static String objectToString(Object obj){
    	return obj == null ? "" : obj.toString();
    }

    public static String cut(String str, int limit) {
		return cut(str, limit, null);
	}

    public static String cut(String str, int limit, String tail) {

    	String returnStr = StringUtils.defaultString(str);

		if(isNotEmpty(tail)) {
			tail = SPACE + tail;
		}

		if (returnStr.length() >= limit) {
			returnStr = str.substring(0, limit) + tail;
		}

		return returnStr;
	}

}
