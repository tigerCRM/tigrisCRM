package com.tiger.crm.repository.dto.analytics;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/*
* AnalyticsWeekDto
* 작성자 : 제예솔
* 설명 : 주간처리현황 데이터 매핑하기 위한 DTO
* */
@Data
public class AnalyticsWeekDto {
    
    //{남유경, [12월 30일(접수 00, 처리 00), 12월 31일(접수 00, 처리 00)] }
    private String userId; //유저 ID
    private String userName;// 유저 이름
    private List<HashMap<String,DayReceiptAndClosedDto>> DayReceiptAndClosedList;

    public AnalyticsWeekDto(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        DayReceiptAndClosedList = null;
    }
}
