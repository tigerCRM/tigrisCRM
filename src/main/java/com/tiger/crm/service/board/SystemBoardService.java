package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;

import java.util.Map;

/*
* 시스템 관리 서비스
* 작성자 : 제예솔
* */
public interface SystemBoardService {

    PagingResponse<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest);
}
