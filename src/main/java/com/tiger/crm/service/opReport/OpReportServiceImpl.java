package com.tiger.crm.service.opReport;

import com.tiger.crm.repository.dto.opReport.OpReportDto;
import com.tiger.crm.repository.mapper.OpReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // 1. 고객 정보 조회
        Map<String, Object> customerInfo = opReportMapper.getCustomerInfo(opReportDto);

        // 2. 지원사 정보 조회
        Map<String, Object> supportCompanyInfo = opReportMapper.getSupportCompanyInfo(opReportDto);

        // 3. 상세 내역 조회
        List<Map<String, Object>> details = opReportMapper.getDetails(opReportDto);

        // 4. OpReportDto에 데이터 바인딩
        OpReportDto resultDto = new OpReportDto();
        resultDto.setCustomerCompany((String) customerInfo.get("COMPANY_NAME")); // 고객사 회사명 설정
        resultDto.setCustomerUserName((String) customerInfo.get("USER_NAME"));   // 고객사 이름 설정
        resultDto.setSupportCompany((String) supportCompanyInfo.get("COMPANY_NAME"));   // 담당자 회사명 설정
        resultDto.setSupportUserName((String) supportCompanyInfo.get("USER_NAME"));      // 담당자 이름 설정
        resultDto.setDetails(details); // 상세 내역 설정

        return resultDto;
    }

}
