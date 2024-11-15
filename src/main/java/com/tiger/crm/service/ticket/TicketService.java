package com.tiger.crm.service.ticket;

import com.tiger.crm.repository.dto.ticket.TicketDto;
import java.util.List;
import java.util.Map;


public interface TicketService {
    List<Map<String, Object>> getTicketList(TicketDto ticketDto);
    int getTicketListCount(TicketDto ticketDto);
}


