package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.ticket.TicketDto;
import java.util.List;
import java.util.Map;


public interface TicketService {
//    List<Map<String, Object>> getTicketList(PagingRequest pagingRequest);
//    int getTicketListCount(TicketDto ticketDto);

    // 페이징 객체를 사용한 리스트
    PagingResponse<Map<String, Object>> getTicketList2(PagingRequest pagingRequest);
}


