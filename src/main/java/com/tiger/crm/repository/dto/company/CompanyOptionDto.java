package com.tiger.crm.repository.dto.company;

import lombok.Data;
/*
 * CompanyOptionDto
 * 작성자 : 제예솔
 * 회사 코드 및 회사이름 selectbox 용으로 만든 dto
 * */
@Data
public class CompanyOptionDto {

    private int companyId;
    private String companyName;
    private char useYn;
}
