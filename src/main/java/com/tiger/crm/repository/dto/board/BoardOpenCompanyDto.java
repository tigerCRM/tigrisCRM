package com.tiger.crm.repository.dto.board;

import lombok.Data;

/*
* BoardOpenCompanyDto
* 작성자 : 제예솔
* 설명 : 시스템관리 게시판의 회사, 공지사항 게시판의 공개 설정여부 회사를 매핑하기 위한 dto
* */
@Data
public class BoardOpenCompanyDto {
    public BoardOpenCompanyDto(){}
    public BoardOpenCompanyDto(int companyId, String companyName){
        this.companyId = companyId;
        this.companyName = companyName;
    }

    private int boardId;
    private int companyId;
    private String companyName;
}
