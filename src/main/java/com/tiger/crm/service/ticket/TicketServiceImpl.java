package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

//    @Override
//    public List<Map<String, Object>> getTicketList(TicketDto ticketDto) {
//        // DTO에서 값을 가져옵니다.
//        String searchKeyword = ticketDto.getSearchKeyword();
//        String searchStatus = ticketDto.getSearchStatus();
//        String searchType = ticketDto.getSearchType();
//        Integer page = ticketDto.getPage();
//        String startDt = ticketDto.getStartDt();
//        String endDt = ticketDto.getEndDt();
//
//        // Mapper를 호출하여 조건에 맞는 티켓 리스트를 반환합니다.
//        return ticketMapper.getTicketList(searchKeyword,searchStatus, searchType, page, startDt, endDt);
//    }
//    @Override
//    public int getTicketListCount(TicketDto ticketDto) {
//        // DTO에서 값을 가져옵니다.
//        String searchKeyword = ticketDto.getSearchKeyword();
//        String searchStatus = ticketDto.getSearchStatus();
//        String searchType = ticketDto.getSearchType();
//        Integer page = ticketDto.getPage();
//        String startDt = ticketDto.getStartDt();
//        String endDt = ticketDto.getEndDt();
//
//        // Mapper를 호출하여 조건에 맞는 티켓 리스트를 반환합니다.
//        return ticketMapper.getTicketListCount(searchKeyword,searchStatus, searchType, page, startDt, endDt);
//    }

    @Override
    public PagingResponse<Map<String, Object>> getTicketList(PagingRequest pagingRequest) {

        List<Map<String, Object>> ticketList = ticketMapper.getTicketList(pagingRequest);
     //   int totalRecords = ticketMapper.getTicketListCount(pagingRequest);

        // 전체 티켓 수 조회 (null 체크 포함)
        int totalRecords = getTicketListCount(pagingRequest);

        // 페이징 응답 객체 생성
        return new PagingResponse<>(ticketList, totalRecords, pagingRequest);
    }
    // 전체 티켓 수를 조회하는 메서드
    private int getTicketListCount(PagingRequest pagingRequest) {
        Integer count = ticketMapper.getTicketListCount(pagingRequest);  // Integer로 받아서 null 체크
        return count != null ? count : 0;  // null일 경우 0 반환
    }

}
