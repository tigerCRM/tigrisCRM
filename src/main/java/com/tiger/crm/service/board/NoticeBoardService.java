package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.NoticeBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;

import java.util.List;
import java.util.Map;

/*
* 시스템 관리 게시판 서비스
* 작성자 : 제예솔
* */


public interface NoticeBoardService {
    
    //시스템 보드 리스트 가져오기
    PagingResponse<Map<String, Object>> getNoticeBoardList(PagingRequest pagingRequest);
    
    //게시글 갯수 가져오기
    int getNoticeBoardListCount(PagingRequest pagingRequest);

    //시스템관리 글 저장
    int insertNoticeBoard(NoticeBoardDto noticeBoardDto, List<BoardOpenCompanyDto> boardOpenCompanyList);

    //첨부파일 저장 후 boardTable 에 첨부파일 아이디 업데이트
    void setNoticeBoardFileId(String fileId,int boardId);

    //게시글번호로 게시글 상세 조회
    NoticeBoardDto getNoticeBoardByBoardId(int boardId);

    //게시글삭제
    void deleteNoticeBoardByBoardId(int boardId);

    //게시글수정
    void updateNoticeBoard(NoticeBoardDto noticeBoardDto);

    //게시판 번호로 boardOpenCompany 찾아오기
    List<BoardOpenCompanyDto> getBoardOpenCompanyByBoardId(int boardId);
}
