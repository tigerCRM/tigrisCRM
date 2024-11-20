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
}
