package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;

import java.util.Map;

/*
* 시스템 관리 게시판 서비스
* 작성자 : 제예솔
* */


public interface SystemBoardService {
    
    //시스템 보드 리스트 가져오기
    PagingResponse<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest);
    
    //게시글 갯수 가져오기
    int getSystemBoardListCount(PagingRequest pagingRequest);

    //시스템관리 글 저장
    int insertSystemBoard(SystemBoardDto systemBoardDto, BoardOpenCompanyDto boardOpenCompanyDto);

    //첨부파일 저장 후 boardTable 에 첨부파일 아이디 업데이트
    void setSystemBoardFileId(String fileId,int boardId);

    //게시글번호로 게시글 상세 조회
    SystemBoardDto getSystemBoardByBoardId(int boardId);

    //게시글삭제
    void deleteSystemBoardByBoardId(int boardId);

    //게시글수정
    void updateSystemBoard(SystemBoardDto systemBoardDto);
}
