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
    private int leftover;//잔여 요청
    private int monReceipt; //요일별 데이터 수
    private int tueReceipt;
    private int wedReceipt;
    private int thuReceipt;
    private int friReceipt;
    private int satReceipt;
    private int sunReceipt;
    private int monComplete;
    private int tueComplete;
    private int wedComplete;
    private int thuComplete;
    private int friComplete;
    private int satComplete;
    private int sunComplete;


}
