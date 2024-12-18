package com.tiger.crm.service.opReport;

import com.tiger.crm.repository.dto.opReport.OpReportDto;

import java.util.List;


public interface OpReportService {

    // 년도 셀렉트 박스 조회
    List<OpReportDto> getYearList(int year);

    // 운영지원 보고서 내용 조회
    OpReportDto getOpReportContent(OpReportDto opReportDto);

}


