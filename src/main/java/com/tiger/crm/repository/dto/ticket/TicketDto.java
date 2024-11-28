package com.tiger.crm.repository.dto.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class TicketDto {
    //검색조건
    private String searchKeyword;
    private String searchStatus;
    private String searchType;
    private String totalcnt;
    private String startDt;
    private String endDt;
    //페이지

    private String title;  //제목
    private String content;  //내용
    private String createId;   //작성자ID
    private String createName;   //작성자ID
    private String managerName;  //담당자이름
    private String managerId;  //담당자ID
    private String companyName;  //고객사명
    private String companyId;  //고객사code
    private String completeDt; //실제완료일
    private String md;        //작업공수
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDt;
  //  private String createDt;
    private String updateId;
    private String updateDt;
    private char deleteYn;
    private String fileId;
    private Integer ticketId;           // TICKET_ID
    private String statusCd;             // STATUS_CD
    private String expectedCompleteDt; // EXPECTED_COMPLETE_DT
    private String realCompleteDt;    // REAL_COMPLETE_DT
    private String priorityYn;           // PRIORITY_YN
    private String parentTicketCd;       // PARENT_TICKET_CD
    private String supportCd;            // SUPPORT_CD
    private String requestTypeCd;        // requestCd

}