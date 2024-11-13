package com.tiger.crm.service;

import java.util.List;
import com.tiger.crm.repository.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    public List<Map<String, Object>> getTicketList(String searchKeyword) {
        return ticketMapper.getTicketList(searchKeyword);
    }
}