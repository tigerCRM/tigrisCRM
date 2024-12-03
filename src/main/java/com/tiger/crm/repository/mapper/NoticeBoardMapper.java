package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.NoticeBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
NoticeBoardMapper
* 작성자 : 제예솔
* 설명 : 시스템게시판 관련 DB 처리
*/
@Mapper
public interface NoticeBoardMapper {

    //시스템 관리 리스트 불러오기
    List<Map<String, Object>> getNoticeBoardList(PagingRequest pagingRequest);

    //시스템 관리 글갯수 불러오기
    Integer getNoticeBoardListCount(PagingRequest pagingRequest);

    //시스템 관리 글저장
    void insertNoticeBoard(NoticeBoardDto noticeBoardDto);

    //시스템 관리 첨부파일 아이디 저장
    void updateNoticeBoardFileId(String fileId, int boardId);

    //시스템게시글 게시글아이디로 찾아오기
    NoticeBoardDto getNoticeBoardByBoardId(int boardId);

    //시스템 게시글 게시글 아이디로 삭제하기(deleteYn = y)
    void deleteNoticeBoardByBoardId(int boardId);

    //시스템 게시글 수정
    void updateNoticeBoard(NoticeBoardDto noticeBoardDto);
}
