package com.tiger.crm.repository.dto.opReport;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OpReportDto {
    private int year;             // 년
    private int opReportId;       // 운영지원보고서 ID
    private int companyId;        // 고객사 코드
    private String createId;      // 생성자 아이디
    private Date createDt;        // 생성일
    private String updateId;      // 수정자 아이디
    private Date updateDt;        // 수정일
    private String supportPeriod;            //지원기간
    private String customerCompany;            // 고객사 이름
    private String customerUserName;           // 고객사 이름
    private String supportCompany;             // 담당자 이름
    private String supportUserName;            // 담당자 이름
    private List<Map<String, Object>> details; // 상세 내역
    private double totalMd;
}