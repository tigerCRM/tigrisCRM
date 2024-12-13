package com.tiger.crm.service.opReport;

import com.tiger.crm.repository.dto.opReport.OpReportDto;

import java.util.List;
import java.util.Map;


public interface OpReportService {

    // 년도별 운영지원 보고서 목록 조회
    List<Map<String, Object>> getOpReportList(OpReportDto opReportDto);

    // 운영지원 보고서 내용 조회
    OpReportDto getOpReportContent(OpReportDto opReportDto);
}


