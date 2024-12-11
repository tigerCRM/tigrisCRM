package com.tiger.crm.repository.dto.board;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
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

    //게시판번호
    private int boardId;
    //회사번호
    @JsonDeserialize(converter = StringToIntConverter.class)
    private int companyId;
    //회사이름
    private String companyName;

    public static class StringToIntConverter extends StdConverter<String, Integer> {
        @Override
        public Integer convert(String value) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return null;  // 예외처리
            }
        }
    }
}
