package com.tiger.crm.service;

import java.util.List;
import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    public List<TicketDto> getTicketList() {
        return ticketMapper.getTicketList();
    }
}