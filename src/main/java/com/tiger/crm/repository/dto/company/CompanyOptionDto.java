package com.tiger.crm.repository.dto.company;

import lombok.Data;

@Data
public class CompanyOptionDto {

    /*
    * CompanyOptionDto
    * 회사 코드 및 회사이름 selectbox 용으로 만든 dto
    * */

    private int companyId;
    private String companyName;
    private char useYn;
}
