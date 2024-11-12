package com.tiger.crm.common.file;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils
{
	public static String makeUploadPath(String userId) {

		String separator = File.separator;

		Date now = new Date();
		String yyyyMM = new SimpleDateFormat("yyyyMM").format(now);
		String dd = new SimpleDateFormat("dd").format(now);

		StringBuilder sb = new StringBuilder();
		sb.append(separator);
		sb.append(yyyyMM).append(separator);
		sb.append(dd).append(separator);
		sb.append(userId).append(separator);

		return sb.toString();
	}

	public static String makeUploadFileName(int seq) {

    	String str = null;
    	String pattern = "yyyyMMddHHmmssSSS";

		try {
		    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
		    str = sdf.format(ts.getTime());
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return str + seq;
    }

	public static void setDisposition(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

		fileName = Normalizer.normalize(fileName, Normalizer.Form.NFC);

		String browser = getBrowser(request.getHeader("User-Agent"));

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE") || browser.equals("Trident")) { // IE11(Trident)
			encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

		} else if (browser.equals("Firefox") || browser.equals("Opera")) {
			encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";

		} else if (browser.equals("Chrome")) {

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}

			encodedFilename = "\"" + sb.toString() + "\"";

		} else {
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

	public static String getBrowser(String userAgent) {

		if (userAgent.indexOf("MSIE") > -1) {
			return "MSIE";

		} else if (userAgent.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";

		} else if (userAgent.indexOf("Chrome") > -1) {
			return "Chrome";

		} else if (userAgent.indexOf("Opera") > -1) {
			return "Opera";

		}

		return "Firefox";
	}
}
