package com.tiger.crm.service.board;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.SystemBoardMapper;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class SystemBoardServiceImpl implements SystemBoardService{

    @Autowired
    private SystemBoardMapper systemBoardMapper;
    @Override
    public PagingResponse<Map<String, Object>> getSystemBoardList(PagingRequest pagingRequest) {

        List<Map<String, Object>> systemBoardList = systemBoardMapper.getSystemBoardList(pagingRequest);

        int totalRecords = getSystemBoardListCount(pagingRequest);

        return new PagingResponse<>(systemBoardList, totalRecords, pagingRequest);
    }

    private int getSystemBoardListCount(PagingRequest pagingRequest) {
        Integer count = systemBoardMapper.getSystemBoardListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }
}
