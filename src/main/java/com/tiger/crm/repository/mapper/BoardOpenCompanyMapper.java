package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import org.apache.ibatis.annotations.Mapper;
/*
* BoardOpenCompanyMapper
* 작성자 : 제예솔
* 설명 : 시스템관리 게시판의 회사, 공지사항 게시판의 공개 설정여부 회사를 매핑
* */
@Mapper
public interface BoardOpenCompanyMapper {
    
    //게시판번호 - 회사 등록
    int insertBoardOpenCompany(BoardOpenCompanyDto boardOpenCompanyDto);
    
    //게시판번호 - 회사 삭제
    void deleteBoardOpenCompany(int boardId);
}
