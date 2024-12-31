package com.tiger.crm.repository.dto.ticket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class TicketDto {
    //검색조건
    private String searchKeyword;   //검색어
    private String searchStatus;    //상태
    private String searchCompany;    //회사검색
    private String searchType;      //검색조건
    private String totalcnt;        //전체건수
    private String startDt;         //시작일자
    private String endDt;           //종료일자
    //페이지
    private String title;           //제목
    private String content;         //내용
    private String createId;        //작성자ID
    private String createName;      //작성자이름
    private String managerId;       //담당자ID
    private String managerName;     //담당자이름
    private String companyName;     //고객사명
    private String companyId;       //고객사code
    private String completeDt;      //실제완료일
    private BigDecimal md;              //작업공수

    private String createDt;     //등록일
    private String updateId;        //수정자ID
    private String updateDt;        //수정일자
    private char deleteYn;          //삭제여부
    private String fileId;          //파일 ID
    private Integer ticketId;       // 요청ID
    private String statusCd;            //진행상태코드
    private String expectedCompleteDt;  //희망완료일
    private String priorityYn;          // 중요도
    private Integer parentTicketCd;      //연관요청
    private String supportCd;           //지원범위
    private String requestTypeCd;       //작업구분
    private List<MultipartFile> attachFiles;    //첨부파일
}