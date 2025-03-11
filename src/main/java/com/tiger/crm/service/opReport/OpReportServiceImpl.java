package com.tiger.crm.service.opReport;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.opReport.OpReportDto;
import com.tiger.crm.repository.mapper.OpReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OpReportServiceImpl implements OpReportService {

    @Autowired
    private OpReportMapper opReportMapper;

    // 년도 셀렉트 박스 조회
    @Override
    public List<OpReportDto> getYearList(int year) {
        return opReportMapper.getYearList(year);
    }

    // 운영지원 보고서 내용 조회
    @Override
    public OpReportDto getOpReportContent(OpReportDto opReportDto) {
        OpReportDto resultDto = new OpReportDto();
        try {
            // 1. 고객 정보 조회
            Map<String, Object> customerInfo = opReportMapper.getCustomerInfo(opReportDto);
            if (customerInfo == null){
                throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", new IllegalStateException("고객사 사용자가 등록되지 않았습니다."));
            }
            // 2. 지원 정보 조회
            Map<String, Object> supportCompanyInfo = opReportMapper.getSupportCompanyInfo(opReportDto);
            // 3. 상세 내역 조회
            List<Map<String, Object>> details = opReportMapper.getDetails(opReportDto);
            // 4. OpReportDto에 데이터 바인딩
            resultDto.setCustomerCompany((String) customerInfo.get("COMPANY_NAME")); // 고객사 회사명 설정
            resultDto.setCustomerUserName((String) customerInfo.get("USER_NAME"));   // 고객사 이름 설정
            resultDto.setSupportCompany((String) supportCompanyInfo.get("COMPANY_NAME"));   // 담당자 회사명 설정
            resultDto.setSupportUserName((String) supportCompanyInfo.get("USER_NAME"));      // 담당자 이름 설정
            // 기존 값
            String opReportId = String.valueOf(opReportDto.getOpReportId());
            // 변환을 위한 날짜 포맷 정의
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년 MM월");
            // 문자열을 Date 객체로 변환
            Date date = inputFormat.parse(opReportId);
            // 원하는 포맷으로 변환
            String formattedDate = outputFormat.format(date);
            resultDto.setSupportPeriod(formattedDate);      //지원기간
            double totalMD = 0.0;

            for (Map<String, Object> detail : details) {
                if (detail.containsKey("MD")) { // "MD" 키가 존재하는지 확인
                    Object value = detail.get("MD");

                    try {
                        double mdValue = Double.parseDouble(value.toString()); // 문자열 등 타입 변환
                        totalMD += mdValue; // 합산
                    } catch (NumberFormatException e) {
                        System.err.println("MD 값 변환 실패: " + value);
                    }
                }
            }
            // 소수점 1자리까지 반올림
            totalMD = Math.round(totalMD * 10) / 10.0;

            resultDto.setTotalMd(totalMD); //MD
            resultDto.setDetails(details); // 상세 내역 설정
        }catch (CustomException e) {
            throw e; // 커스텀 예외 그대로 전파
        } catch (Exception e) {
            throw new CustomException("예기치 않은 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
        return resultDto;
    }

}
