package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.opReport.OpReportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OpReportMapper {

    // 년도별 운영지원 보고서 목록 조회
    List<Map<String, Object>> getOpReportList(OpReportDto opReportDto);

    // 고객 정보 조회
    Map<String, Object> getCustomerInfo(OpReportDto opReportDto);

    // 지원사 정보 조회
    Map<String, Object> getSupportCompanyInfo(OpReportDto opReportDto);

    // 상세 내역 조회
    List<Map<String, Object>> getDetails(OpReportDto opReportDto);
}