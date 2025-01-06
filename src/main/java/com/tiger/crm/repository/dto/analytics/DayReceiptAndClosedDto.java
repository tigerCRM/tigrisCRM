package com.tiger.crm.repository.dto.analytics;

import lombok.Data;

/*
 * DayReceiptAndClosedDto
 * 작성자 : 제예솔
 * 설명 : 날짜별 접수 갯수, 처리 갯수
 * */
@Data
public class DayReceiptAndClosedDto {

    private Integer receipt;
    private Integer closed;


}
