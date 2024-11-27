package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.board.BoardOpenCompanyDto;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;

import java.util.Map;

/*
* 시스템 관리 서비스
* 작성자 : 제예솔
* */


public interface SystemBoardService {

    PagingResponse<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest);
    int getSystemBoardListCount(PagingRequest pagingRequest);
    int insertSystemBoard(SystemBoardDto systemBoardDto, BoardOpenCompanyDto boardOpenCompanyDto);
    void setSystemBoardFileId(String fileId,int boardId);
    SystemBoardDto getSystemBoardByBoardId(int boardId);
    void deleteSystemBoardByBoardId(int boardId);
}
