package com.tiger.crm.service.common;

import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.ticket.StatusMapper;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.CompanyOptionMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*공통 서비스
 * 설명 : 공통서비스 모음집
 * 1. selectbox
 * 2. 회사 불러오기(getCompanyOption)
 * 2. downloadExcel
 * 3.
 * */

@Service
@PropertySource("classpath:setting.properties")
@ConfigurationProperties(prefix = "code")
public class CommonService {
    @Autowired
    CompanyOptionMapper companyOptionMapper;
    private Map<String, String> select = new LinkedHashMap<>();

    public Map<String, String> getSelectOptions(String id) {
        Map<String, String> options = new LinkedHashMap<>();
        String selectedOptions = select.get(id);  // id로 옵션 문자열 가져오기
        if (selectedOptions != null) {
            String[] optionsArray = selectedOptions.split(",");
            for (String option : optionsArray) {
                String[] parts = option.split(":");
                options.put(parts[0], parts[1]);
            }
        }
        return options;
    }

    public void setSelect(Map<String, String> select) {
        this.select = select;
    }

    /*
    * 회사 이름 불러오기
    * 작성자 : 제예솔
    * 설명 : select box 에서 사용하기 위해 회사 이름, 회사 아이디를 불러옴
    * */
    public List<CompanyOptionDto> getCompanyOption() {
        return companyOptionMapper.getAllCompany();
    }

    // 고객사별 회사 불러오기
    public List<CompanyOptionDto> getCompanyOption2(CompanyOptionDto companyOptionDto) {
        return companyOptionMapper.getAllCompany2(companyOptionDto);
    }


    public void downloadExcel(List<Map<String, Object>> dataList, HttpServletResponse response, String location) throws IOException {
        // Excel 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // CellStyle 생성 (헤더 스타일)
        CellStyle headerStyle = workbook.createCellStyle();
        // 배경색 설정 (예: 파란색)
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 글자색 설정 (예: 흰색)
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        // 헤더 생성
        if (Objects.equals(location, "ticketList")){
            // 헤더 컬럼명 지정 (고정값)
            String[] headerColumns = {"요청번호", "고객사", "제목", "분류", "상태", "범위", "담당", "MD", "등록일", "완료일"};
            Row headerRow = sheet.createRow(0);
            int headerIndex = 0;
            for (String columnName : headerColumns) {
                Cell cell = headerRow.createCell(headerIndex++);
                cell.setCellValue(columnName);
                cell.setCellStyle(headerStyle);  // 헤더 셀에 스타일 적용
            }
            // 데이터 행 생성
            int rowNum = 1;
            for (Map<String, Object> data : dataList) {
                Row row = sheet.createRow(rowNum++);
                int cellIndex = 0;

                String requestTypeCd = data.get("REQUEST_TYPE_CD") != null ? data.get("REQUEST_TYPE_CD").toString() : "";
                String displayRequestTypeCd = StatusMapper.getRequestText(requestTypeCd);

                String statusCd = data.get("STATUS_CD") != null ? data.get("STATUS_CD").toString() : "";
                String displayProcessCd = StatusMapper.getStatusText(statusCd);

                String supportCd = data.get("SUPPORT_CD") != null ? data.get("SUPPORT_CD").toString() : "";
                String displaySupportCd = StatusMapper.getSupportText(supportCd);
                // 각 열의 순서대로 데이터를 넣기
                row.createCell(cellIndex++).setCellValue(data.get("TICKET_ID") != null ? data.get("TICKET_ID").toString() : "");
                row.createCell(cellIndex++).setCellValue(data.get("COMPANY_NAME") != null ? data.get("COMPANY_NAME").toString() : "");
                row.createCell(cellIndex++).setCellValue(data.get("TITLE") != null ? data.get("TITLE").toString() : "");
                row.createCell(cellIndex++).setCellValue(displayRequestTypeCd);
                row.createCell(cellIndex++).setCellValue(displayProcessCd);
                row.createCell(cellIndex++).setCellValue(displaySupportCd);
                row.createCell(cellIndex++).setCellValue(data.get("USER_NAME") != null ? data.get("USER_NAME").toString() : "");
                row.createCell(cellIndex++).setCellValue(data.get("MD") != null ? data.get("MD").toString() : "");
                row.createCell(cellIndex++).setCellValue(data.get("CREATE_DT") != null ? data.get("CREATE_DT").toString() : "");
                row.createCell(cellIndex++).setCellValue(data.get("COMPLETE_DT") != null ? data.get("COMPLETE_DT").toString() : "");
            }
        }else{
            if (!dataList.isEmpty()) {
                Row headerRow = sheet.createRow(0);
                int headerIndex = 0;
                for (String key : dataList.get(0).keySet()) {
                    Cell cell = headerRow.createCell(headerIndex++);
                    cell.setCellValue(key);
                    cell.setCellStyle(headerStyle);  // 헤더 셀에 스타일 적용
                }
                // 데이터 행 생성
                int rowNum = 1;
                for (Map<String, Object> data : dataList) {
                    Row row = sheet.createRow(rowNum++);
                    int cellIndex = 0;
                    for (Object value : data.values()) {
                        row.createCell(cellIndex++).setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

        }



        // 현재 시간으로 파일 이름 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        String filename = location + formattedDate + ".xlsx";
        // 응답 헤더 설정 및 파일 전송
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        // 파일 작성 후 응답에 출력
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().flush();
    }

    // Session에서 사용자 ID 가져오기
    public UserLoginDto getCurrentUserIdFromSession() {
        // RequestContextHolder로 현재 HTTP 요청의 세션 가져오기
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
        if (session != null) {
            UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");
            if (loginUser != null) {
                return loginUser;
            }
        }
        return null;
    }
}
