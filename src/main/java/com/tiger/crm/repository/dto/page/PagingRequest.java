package com.tiger.crm.repository.dto.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingRequest {
    private int page;                      // 현재 페이지 번호
    private int size;                      // 한 페이지에 보여줄 데이터 개수
    private String searchKeyword;          // 검색어
    private String searchType;             // 검색 타입
    private String startDt;                // 시작일
    private String endDt;                  // 종료일
    private String searchStatus;           // 요청상태
    private String searchCompany;          // 회사 검색
    private String userClass;              // 사용자 권한
    private String userName;               // 사용자 이름
    private String userId;                 // 사용자 아이디
    private String companyId;              // 회사 코드
    private int totalcnt;
    private String createId;
    private String searchSupportCd;        //범위
    private String satisfaction;    //만족도 조사
    private String searchManager;   //담당자검색
    
    public PagingRequest() {
        this.page = 1;  // 기본값 설정
        this.size = 10; // 기본값 설정
    }

    public int getOffset() {
        return (page - 1) * size;
    }

}