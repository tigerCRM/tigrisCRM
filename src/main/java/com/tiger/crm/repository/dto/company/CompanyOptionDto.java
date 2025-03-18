package com.tiger.crm.repository.dto.company;

import lombok.Data;
/*
 * CompanyOptionDto
 * 작성자 : 제예솔
 * 회사 코드 및 회사이름 selectbox 용으로 만든 dto
 * + 담당자 추가
 * */
@Data
public class CompanyOptionDto {
    
    //회사아이디
    private int companyId;
    //회사이름
    private String companyName;
    //사용여부
    private char useYn;
    // 담당자 ID , 이름
    private String userId;
    private String userName;
}
