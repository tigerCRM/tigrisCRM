package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.opReport.OpReportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OpReportMapper {

    // 고객 정보 조회
    Map<String, Object> getCustomerInfo(OpReportDto opReportDto);

    // 년도 셀렉트 박스 조회
    List<OpReportDto> getYearList(int year);

    // 지원사 정보 조회
    Map<String, Object> getSupportCompanyInfo(OpReportDto opReportDto);

    // 상세 내역 조회
    List<Map<String, Object>> getDetails(OpReportDto opReportDto);

}