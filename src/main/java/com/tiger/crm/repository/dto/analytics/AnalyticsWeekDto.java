package com.tiger.crm.repository.dto.analytics;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/*
* AnalyticsWeekDto
* 작성자 : 제예솔
* 설명 : 주간처리현황 데이터 매핑하기 위한 DTO
* 접수 및 처리는 같은 DTO를 사용하되 조회하는 쿼리만 다르다
* */
@Data
public class AnalyticsWeekDto {
    
    private String userId; //유저 ID
    private String userName;// 유저 이름
    private Integer leftover;//잔여 요청
    private Integer monReceipt; //요일별 데이터 수
    private Integer tueReceipt;
    private Integer wedReceipt;
    private Integer thuReceipt;
    private Integer friReceipt;
    private Integer satReceipt;
    private Integer sunReceipt;
    private Integer monComplete;
    private Integer tueComplete;
    private Integer wedComplete;
    private Integer thuComplete;
    private Integer friComplete;
    private Integer satComplete;
    private Integer sunComplete;


}
