package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.ticket.TicketDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<Map<String, Object>> getTicketList(TicketDto ticketDto) {
        // DTO에서 값을 가져옵니다.
        String searchKeyword = ticketDto.getSearchKeyword();
        String searchStatus = ticketDto.getSearchStatus();
        String searchType = ticketDto.getSearchType();
        Integer page = ticketDto.getPage();
        String startDt = ticketDto.getStartDt();
        String endDt = ticketDto.getEndDt();

        // Mapper를 호출하여 조건에 맞는 티켓 리스트를 반환합니다.
        return ticketMapper.getTicketList(searchKeyword,searchStatus, searchType, page, startDt, endDt);
    }
    @Override
    public int getTicketListCount(TicketDto ticketDto) {
        // DTO에서 값을 가져옵니다.
        String searchKeyword = ticketDto.getSearchKeyword();
        String searchStatus = ticketDto.getSearchStatus();
        String searchType = ticketDto.getSearchType();
        Integer page = ticketDto.getPage();
        String startDt = ticketDto.getStartDt();
        String endDt = ticketDto.getEndDt();

        // Mapper를 호출하여 조건에 맞는 티켓 리스트를 반환합니다.
        return ticketMapper.getTicketListCount(searchKeyword,searchStatus, searchType, page, startDt, endDt);
    }
}
