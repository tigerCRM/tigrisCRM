package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
시스템관리 매퍼
* 작성자 : 제예솔
*/
@Mapper
public interface SystemBoardMapper {

    //시스템 관리 리스트 불러오기
    List<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest);

    //시스템 관리 글갯수 불러오기
    Integer getSystemBoardListCount(PagingRequest pagingRequest);

    //시스템 관리 글저장
    void insertSystemBoard(SystemBoardDto systemBoardDto);

    //시스템 관리 첨부파일 아이디 저장
    void updateSystemBoardFileId(String fileId, int boardId);

    //시스템게시글 게시글아이디로 찾아오기
    SystemBoardDto getSystemBoardByBoardId(int boardId);

    //시스템 게시글 게시글 아이디로 삭제하기(deleteYn = y)
    void deleteSystemBoardByBoardId(int boardId);
}
