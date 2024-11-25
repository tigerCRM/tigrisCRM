package com.tiger.crm.repository.dto.board;

import lombok.Data;

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
