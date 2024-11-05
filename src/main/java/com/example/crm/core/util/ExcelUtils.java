//package com.example.crm.core.util;
//
//import jxl.Workbook;
//import jxl.format.*;
//import jxl.write.*;
//import jxl.write.biff.RowsExceededException;
//import org.springframework.util.FileCopyUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;
//
///**
// * <code>ExcelUtils</code>
// *
// * @author Jaehwan Lee
// * @since 2016. 4. 22
// * @version 1.0
// */
//public class ExcelUtils {
//
//
//	private ExcelUtils() {
//	}
//
//	public static File makeExcel(String fileTitle, TigrisMap periodMap, String targetPath, List<String> labelList, List<String> columnList, List<TigrisMap> data) throws IOException, RowsExceededException, WriteException {
//		int labelRow = 0;
//		File path = new File(targetPath);
//		if (!path.exists()) path.mkdirs();
//		File excelFile = new File(path, ""+System.currentTimeMillis());
//
//		WritableWorkbook myWorkbook = Workbook.createWorkbook(excelFile);
//		WritableSheet mySheet = myWorkbook.createSheet("Sheet1", 0);
//
//		WritableCellFormat titleFormat = new WritableCellFormat();
//		titleFormat.setAlignment(Alignment.CENTRE);
//		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		titleFormat.setFont(new WritableFont (WritableFont.ARIAL, 20, WritableFont.BOLD));
//
//		WritableCellFormat headerFormat = new WritableCellFormat();
//		headerFormat.setAlignment(Alignment.CENTRE);
//		headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//		headerFormat.setBackground(Colour.GRAY_25);
//		headerFormat.setFont(new WritableFont (WritableFont.ARIAL, 10, WritableFont.BOLD));
//
//		WritableCellFormat textFormat = new WritableCellFormat();
//		WritableCellFormat intFormat = new WritableCellFormat();
//		WritableCellFormat floatFormat = new WritableCellFormat();
//
//		textFormat.setAlignment(Alignment.LEFT);
//		textFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//
//		intFormat.setAlignment(Alignment.RIGHT);
//		intFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		intFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//
//		floatFormat.setAlignment(Alignment.RIGHT);
//		floatFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		floatFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
//
//		Label label;
//		if (fileTitle != null) {
//			label = new Label(0, labelRow, fileTitle, titleFormat);
//			mySheet.addCell(label);
//			mySheet.mergeCells(0, 0, labelList.size()-1, 0);
//			labelRow += 2;
//		}
//
//		if(periodMap !=null) {
//			label = new Label(0, labelRow, periodMap.get("periodName").toString() , headerFormat);
//			mySheet.addCell(label);
//			label = new Label(1, labelRow, periodMap.get("periodValue").toString(), textFormat);
//			mySheet.addCell(label);
//			mySheet.mergeCells(1, labelRow, labelList.size()-1, labelRow);
//			labelRow++;
//		}
//
//		for (int i=0; i<labelList.size(); i++) {
//			String labelText = labelList.get(i);
//			label = new Label(i, labelRow, labelText, headerFormat);
//
//			mySheet.setColumnView(i, 24);
//			mySheet.addCell(label);
//		}
//		for (int i=0; i<data.size(); i++) {
//			Map map = data.get(i);
//			for (int col=0; col<columnList.size(); col++) {
//				String mapIndex = columnList.get(col);
//				Object column = map.get(mapIndex);
//
//				String value;
//				WritableCellFormat format;
//				if (column == null) {
//					value = "";
//					format = textFormat;
//				} else if (column instanceof BigDecimal || column instanceof Integer || column instanceof Long) {
//					value = column.toString();
//					format = intFormat;
//				} else if (column instanceof Float) {
//					value = column.toString();
//					format = floatFormat;
//				} else {
//					value = column.toString();
//					format = textFormat;
//				}
//				label = new Label(col, i+labelRow+1, value, format);
//				mySheet.addCell(label);
//			}
//		}
//		myWorkbook.write();
//		myWorkbook.close();
//		return excelFile;
//	}
//
//	public static void getDownload(String fileName, File uFile,	HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		int fSize = (int) uFile.length();
//
//		if (fSize > 0) {
//			String mimetype = "application/x-msdownload";
//
//			response.setContentType(mimetype);
//			setDisposition(fileName, request, response);
//			response.setContentLength(fSize);
//
//			BufferedInputStream in = null;
//			BufferedOutputStream out = null;
//
//			try {
//				in = new BufferedInputStream(new FileInputStream(uFile));
//				out = new BufferedOutputStream(response.getOutputStream());
//
//				FileCopyUtils.copy(in, out);
//				out.flush();
//			} catch (Exception ex) {
//			} finally {
//				if (in != null) {
//					try {
//						in.close();
//					} catch (Exception ignore) {
//					}
//				}
//				if (out != null) {
//					try {
//						out.close();
//					} catch (Exception ignore) {
//					}
//				}
//			}
//
//		} else {
//			response.setContentType("application/x-msdownload");
//
//			PrintWriter printwriter = response.getWriter();
//			printwriter.println("<html>");
//			printwriter.println("<br><br><br><h2>Could not get file name:<br>"
//					+ fileName + "</h2>");
//			printwriter
//					.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
//			printwriter.println("<br><br><br>&copy; webAccess");
//			printwriter.println("</html>");
//			printwriter.flush();
//			printwriter.close();
//		}
//	}
//
//	public static void setDisposition(String filename,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String browser = getBrowser(request);
//
//		String dispositionPrefix = "attachment; filename=";
//		String encodedFilename = null;
//
//		if (browser.equals("MSIE")) {
//			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
//					"\\+", "%20");
//		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
//			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
//					"\\+", "%20");
//		} else if (browser.equals("Firefox")) {
//			encodedFilename = "\""
//					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
//		} else if (browser.equals("Opera")) {
//			encodedFilename = "\""
//					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
//		} else if (browser.equals("Chrome")) {
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < filename.length(); i++) {
//				char c = filename.charAt(i);
//				if (c > '~') {
//					sb.append(URLEncoder.encode("" + c, "UTF-8"));
//				} else {
//					sb.append(c);
//				}
//			}
//			encodedFilename = "\"" + sb.toString() + "\"";
//		} else {
//			throw new IOException("Not supported browser");
//		}
//
//		response.setHeader("Content-Disposition", dispositionPrefix
//				+ encodedFilename);
//
//		if ("Opera".equals(browser)) {
//			response.setContentType("application/octet-stream;charset=UTF-8");
//		}
//	}
//
//	public static String getBrowser(HttpServletRequest request) {
//		String header = request.getHeader("User-Agent");
//		if (header.indexOf("MSIE") > -1) {
//			return "MSIE";
//		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
//			return "Trident";
//		} else if (header.indexOf("Chrome") > -1) {
//			return "Chrome";
//		} else if (header.indexOf("Opera") > -1) {
//			return "Opera";
//		}
//		return "Firefox";
//	}
//
//
//}
